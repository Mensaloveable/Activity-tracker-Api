package com.loveable.tracker.repositories;

import com.loveable.tracker.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitle(String title);

    Task findTaskByTitle(String title);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1",
            nativeQuery = true
    )
    Optional<List<Task>> findAllTasksByUserUuid(String uuid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM task WHERE title = ?1",
            nativeQuery = true
    )
    int deleteTaskByTitle(String title);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = ?2",
            nativeQuery = true
    )
    List<Task> viewUserTasksByStatus(String userUuid, int status);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 0",
            nativeQuery = true
    )
    List<Task> viewUserInProgressTasks(String userUuid);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 1",
            nativeQuery = true
    )
    List<Task> viewUserPendingTasks(String userUuid);

    @Query(
            value = "SELECT task.* FROM task JOIN users u on u.id = task.user_id WHERE u.uuid = ?1 AND status = 2",
            nativeQuery = true
    )
    List<Task> viewUserDoneTasks(String userUuid);
}
