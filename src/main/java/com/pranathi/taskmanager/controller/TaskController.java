package com.pranathi.taskmanager.controller;

import com.pranathi.taskmanager.dto.ApiResponse;
import com.pranathi.taskmanager.dto.TaskResponse;
import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.pranathi.taskmanager.dto.TaskCreateRequest;

import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger =
            LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request) {
        logger.info("Received request to create task");

        taskService.createTask(
                request.getTitle(),
                request.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Task created successfully"));

    }
    @GetMapping
    public ResponseEntity<Page<TaskResponse>> getAllTasks(@PageableDefault(size = 5) Pageable pageable) {

        Page<Task> taskPage = taskService.getAllTasks(pageable);

        Page<TaskResponse> responsePage = taskPage.map(task -> {
            TaskResponse dto = new TaskResponse();
            dto.setId(task.getId());
            dto.setTitle(task.getTitle());
            dto.setDescription(task.getDescription());
            return dto;
        });

        return ResponseEntity.ok(responsePage);
    }

}
