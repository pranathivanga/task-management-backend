package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
}
