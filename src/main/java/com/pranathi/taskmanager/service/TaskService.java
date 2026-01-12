package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.dto.TaskCreateRequest;
import com.pranathi.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final List<TaskCreateRequest> tasks = new ArrayList<>();

    public void createTask(String title, String description) {
        TaskCreateRequest task = new TaskCreateRequest();
        task.setTitle(title);
        task.setDescription(description);
        tasks.add(task);
    }

    public List<TaskCreateRequest> getAllTasks() {
        return tasks;
    }
}

