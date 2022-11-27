package com.loveable.tracker.serviceImpl;

import com.loveable.tracker.dto.TaskDto;
import com.loveable.tracker.dto.TaskResponseDto;
import com.loveable.tracker.enums.Status;
import com.loveable.tracker.model.Task;
import com.loveable.tracker.model.User;
import com.loveable.tracker.repositories.TaskRepository;
import com.loveable.tracker.repositories.UserRepository;
import com.loveable.tracker.services.TaskService;
import com.loveable.tracker.utils.ApiResponse;
import com.loveable.tracker.utils.ResponseManager;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final ModelMapper mapper;
    private final ResponseManager<TaskResponseDto> responseManager;
    private final ResponseManager<List<TaskResponseDto>> listResponseManager;
    private final TaskRepository taskRepo;
    private final UserRepository userRepo;

    @Override
    public ApiResponse createTask(TaskDto taskDto) {
        Optional<User> user = userRepo.findByUuid(taskDto.getUuid());
        if (taskDto.getTitle() == null)
            return responseManager.error("Title is Required", BAD_REQUEST);
        else if (taskDto.getDescription() == null)
            return responseManager.error("Description is required", BAD_REQUEST);
        boolean titleExist = taskRepo.existsByTitle(taskDto.getTitle());
        if (titleExist)
            return responseManager.error("Title Already Exist", BAD_REQUEST);
        if (user.isPresent()) {
            System.out.println(user.get());
            taskDto.setUser(user.get());
            Task task = mapper.map(taskDto, Task.class);
            taskRepo.save(task);
            TaskResponseDto taskResponseDto = mapper.map(task, TaskResponseDto.class);
            return responseManager.success(taskResponseDto, CREATED);
        }
        return responseManager.error("User with uuid does not exist", BAD_REQUEST);
    }

    @Override
    public ApiResponse viewTask(String userUuid, String title) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (title == null)
            return responseManager.error("You've not entered your task title", BAD_REQUEST);
        if (user.isPresent()) {
//            System.out.println(user.get());
            Task task = taskRepo.findTaskByTitle(title);
            TaskResponseDto taskResponse = mapper.map(task, TaskResponseDto.class);
            return responseManager.success(taskResponse, FOUND);
        }
        return responseManager.error("User does not exist", BAD_REQUEST);
    }

    @Override
    public ApiResponse viewAllUserTask(String userUuid) {
        ResponseManager<List<TaskResponseDto>> rm = new ResponseManager<>();
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (user.isPresent()) {
            List<TaskResponseDto> responseDtoList = new ArrayList<>();
            Optional<List<Task>> allTasksByUser = taskRepo.findAllTasksByUserUuid(userUuid);
            if (allTasksByUser.isPresent()) {
                allTasksByUser.get().forEach(task -> {
                    TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(responseDto);
                });
                if (responseDtoList.size() == 0) {
                    return rm.error("No task created yet", OK);
                }
                return rm.success(responseDtoList, FOUND);
            }
        }
        return rm.error("No User with " + userUuid + " found", NOT_FOUND);
    }

    @Override
    public ApiResponse deleteTask(String userUuid, String title) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (title == null)
            return responseManager.error("You have not specified the task to delete", BAD_REQUEST);
        if (user.isPresent()) {
            int del = taskRepo.deleteTaskByTitle(title);
            if (del < 0)
                return responseManager.error("No such task", NOT_FOUND);
            return responseManager.success(null, ACCEPTED);
        } else {
            return responseManager.error("You're not a user", UNAUTHORIZED);
        }
    }

    @Override
    public ApiResponse viewUserTasksByStatus(String userUuid, int status) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (user.isPresent()) {
            List<Task> tasks = taskRepo.viewUserTasksByStatus(userUuid, status);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto responseDto = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(responseDto);
                });
                return listResponseManager.success(responseDtoList, FOUND);
            } else {
                return responseManager.error("No task in " + Status.values()[status] + " category", NOT_FOUND);
            }
        }
        return responseManager.error("You are not a user", NOT_ACCEPTABLE);
    }

    @Override
    public ApiResponse viewUserInProcessTasks(String userUuid) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (user.isPresent()) {
            List<Task> tasks = taskRepo.viewUserInProgressTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return listResponseManager.success(responseDtoList, FOUND);
            }
            return responseManager.error("No task in IN_PROGRESS category", NOT_FOUND);
        }
        return responseManager.error("You are not a user", NOT_ACCEPTABLE);
    }

    @Override
    public ApiResponse viewUserPendingTasks(String userUuid) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (user.isPresent()) {
            List<Task> tasks = taskRepo.viewUserPendingTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return listResponseManager.success(responseDtoList, FOUND);
            }
            return responseManager.error("No task in PENDING category", NOT_FOUND);
        }
        return responseManager.error("You are not a user", NOT_ACCEPTABLE);
    }

    @Override
    public ApiResponse viewUserDoneTasks(String userUuid) {
        Optional<User> user = userRepo.findByUuid(userUuid);
        if (user.isPresent()) {
            List<Task> tasks = taskRepo.viewUserDoneTasks(userUuid);
            if (tasks.size() > 0) {
                List<TaskResponseDto> responseDtoList = new ArrayList<>();
                tasks.forEach(task -> {
                    TaskResponseDto map = mapper.map(task, TaskResponseDto.class);
                    responseDtoList.add(map);
                });
                return listResponseManager.success(responseDtoList, FOUND);
            }
            return responseManager.error("No task in DONE category", NOT_FOUND);
        }
        return responseManager.error("You are not a user", NOT_ACCEPTABLE);
    }


}
