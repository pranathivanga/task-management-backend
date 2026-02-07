package com.pranathi.taskmanager.controller;

import com.pranathi.taskmanager.dto.ApiResponse;
import com.pranathi.taskmanager.dto.TaskCreateRequest;
import com.pranathi.taskmanager.dto.TaskUpdateRequest;
import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ✅ CREATE TASK — NO userId FROM CLIENT
    @PostMapping
    public ResponseEntity<ApiResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request) {

        logger.info("Creating task for logged-in user");

        taskService.createTask(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getIdempotencyKey()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Task created successfully"));
    }

    // ✅ GET ONLY LOGGED-IN USER'S TASKS
    @GetMapping
    public ResponseEntity<ApiResponse> getMyTasks() {

        List<Task> tasks = taskService.getMyTasks();

        return ResponseEntity.ok(
                new ApiResponse<>("My tasks fetched successfully", tasks)
        );
    }

    // ⚠️ UPDATE TASK (ownership check comes Day 13)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {

        taskService.updateTask(id, request.getStatus(), request.getDescription());

        return ResponseEntity.ok(
                new ApiResponse<>("Task updated successfully")
        );
    }

    // ⚠️ DELETE TASK (ownership check comes Day 13)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.ok(
                new ApiResponse<>("Task deleted successfully")
        );
    }
}
