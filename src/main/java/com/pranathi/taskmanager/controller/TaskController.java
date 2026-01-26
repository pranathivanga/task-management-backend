package com.pranathi.taskmanager.controller;

import com.pranathi.taskmanager.dto.ApiResponse;
import com.pranathi.taskmanager.dto.TaskResponse;
import com.pranathi.taskmanager.dto.TaskUpdateRequest;
import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.pranathi.taskmanager.dto.TaskCreateRequest;

import java.util.List;


@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request) {

        logger.info("Received request to create task for user {}", request.getUserId());

        taskService.createTask(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getUserId(),
                request.getIdempotencyKey()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Task created successfully"));
    }
    @GetMapping
    public ResponseEntity<ApiResponse> getAllTasks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long userId,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(
                new ApiResponse<>("Tasks fetched", taskService.getAllTasks(keyword, status, userId, pageable))
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request) {

        taskService.updateTask(id, request.getStatus(), request.getDescription());

        return ResponseEntity.ok(new ApiResponse<>("Task updated successfully"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {

        taskService.deleteTask(id);

        return ResponseEntity.ok(new ApiResponse<>("Task deleted successfully"));
    }
    @GetMapping("/users/{userId}/tasks-with-user")
    public ResponseEntity<ApiResponse> getTasksWithUser(@PathVariable Long userId) {

        return ResponseEntity.ok(
                new ApiResponse<>("Tasks with user info",
                        taskService.getTasksWithUser(userId))
        );
    }

}
