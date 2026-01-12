package com.pranathi.taskmanager.controller;

import com.pranathi.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<String> createTask(
            @RequestBody TaskCreateRequest request) {

        taskService.createTask(
                request.getTitle(),
                request.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Task created successfully");
    }
    @GetMapping
    public ResponseEntity<List<TaskCreateRequest>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

}
