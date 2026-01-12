package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.dto.TaskCreateRequest;
import com.pranathi.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private static final Logger logger= LoggerFactory.getLogger(TaskService.class);
    private final List<TaskCreateRequest> tasks = new ArrayList<>();


    public void createTask(String title, String description) {
        logger.info("Creating task with title: {}", title);


        TaskCreateRequest task = new TaskCreateRequest();
        task.setTitle(title);
        task.setDescription(description);
        tasks.add(task);
    }

    public List<TaskCreateRequest> getAllTasks() {
        return tasks;
    }
}

