package com.loveable.tracker.controllers;

import com.loveable.tracker.dto.TaskDto;
import com.loveable.tracker.services.TaskService;
import com.loveable.tracker.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create_task")
    public ApiResponse createTask(@RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    @GetMapping("/find_task/{userUuid}/{title}")
    public ApiResponse findTaskPath(@PathVariable String userUuid, @PathVariable String title) {
        return taskService.viewTask(userUuid, title);
    }

    @GetMapping("/find_task")
    //localhost:8083/find_task?q=96023180&q=Testing
    public ApiResponse findTask(@RequestParam("q1") String userUuid, @RequestParam("q2") String title) {
        return taskService.viewTask(userUuid, title);
    }

    @GetMapping("/get_all_tasks/{uuid}")
    public ApiResponse getAllTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewAllUserTask(userUuid);
    }

    @GetMapping("/get_all_tasks")
    //localhost:8083/get_all_tasks?q=E42F1740
    public ApiResponse getAllTasksParam(@RequestParam("q") String userUuid) {
        return taskService.viewAllUserTask(userUuid);
    }

    @DeleteMapping("/delete_task/{uuid}/{title}")
    public ApiResponse deleteTask(@PathVariable("uuid") String userUuid, @PathVariable String title) {
        return taskService.deleteTask(userUuid, title);
    }

    @DeleteMapping("/delete_task")
    //localhost:8083/delete_task?q=E42F1740&q=WoW
    public ApiResponse deleteATask(@RequestParam("q1") String userUuid, @RequestParam("q2") String title) {
        return taskService.deleteTask(userUuid, title);
    }

    @GetMapping("/get_tasks_by_status/{uuid}/{status}")
    public ApiResponse getTasksByStatus(@PathVariable("uuid") String userUuid, @PathVariable int status) {
        return taskService.viewUserTasksByStatus(userUuid, status);
    }

    @GetMapping("/get_tasks_by_status")
    public ApiResponse getAllTasksByStatus(@RequestParam("q1") String userUuid, @RequestParam("q2") int status) {
        return taskService.viewUserTasksByStatus(userUuid, status);
    }

    @GetMapping("/get_in_progress_tasks/{uuid}")
    public ApiResponse getInProgressTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserInProcessTasks(userUuid);
    }

    @GetMapping("/get_in_progress_tasks")
    public ApiResponse ViewInProgressTasks(@RequestParam("q") String userUuid) {
        return taskService.viewUserInProcessTasks(userUuid);
    }

    @GetMapping("/get_pending_tasks/{uuid}")
    public ApiResponse getPendingTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserPendingTasks(userUuid);
    }

    @GetMapping("/get_pending_tasks")
    public ApiResponse viewPendingTasks(@RequestParam("q") String userUuid) {
        return taskService.viewUserPendingTasks(userUuid);
    }

    @GetMapping("/get_done_tasks/{uuid}")
    public ApiResponse getDoneTasks(@PathVariable("uuid") String userUuid) {
        return taskService.viewUserDoneTasks(userUuid);
    }

    @GetMapping("/get_done_tasks")
    public ApiResponse viewDoneTasks(@RequestParam("q") String userUuid) {
        return taskService.viewUserDoneTasks(userUuid);
    }
}
