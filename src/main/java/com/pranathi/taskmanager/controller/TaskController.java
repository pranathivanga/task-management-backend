package com.pranathi.taskmanager.controller;

import com.pranathi.taskmanager.dto.ApiResponse;
import com.pranathi.taskmanager.dto.TaskResponse;
import com.pranathi.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.pranathi.taskmanager.dto.TaskCreateRequest;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request) {

        taskService.createTask(
                request.getTitle(),
                request.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Task created successfully"));

    }
    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {

        List<TaskResponse> response = taskService.getAllTasks()
                .stream()
                .map(task -> {
                    TaskResponse dto = new TaskResponse();
                    dto.setId(task.getId());
                    dto.setTitle(task.getTitle());
                    dto.setDescription(task.getDescription());
                    return dto;
                })
                .toList();

        return ResponseEntity.ok(response);
    }

}
