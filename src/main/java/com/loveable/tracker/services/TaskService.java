package com.loveable.tracker.services;

import com.loveable.tracker.dto.TaskDto;
import com.loveable.tracker.utils.ApiResponse;

public interface TaskService {
    ApiResponse createTask(TaskDto task);

    ApiResponse viewTask(String userUuid, String title);

    ApiResponse viewAllUserTask(String userUuid);

    ApiResponse deleteTask(String userUuid, String title);

    ApiResponse viewUserTasksByStatus(String userUuid, int status);

    ApiResponse viewUserInProcessTasks(String userUuid);

    ApiResponse viewUserPendingTasks(String userUuid);

    ApiResponse viewUserDoneTasks(String userUuid);
}
