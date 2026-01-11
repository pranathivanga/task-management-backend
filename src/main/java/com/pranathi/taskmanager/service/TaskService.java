package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository){

        this.taskRepository=taskRepository;
    }
    public void createTask(String title, String description) {
        // business logic will grow later
        System.out.println("Creating task: " + title);
    }

}
