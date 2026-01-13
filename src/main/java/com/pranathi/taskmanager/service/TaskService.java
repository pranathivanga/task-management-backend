package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(String title, String description) {

        logger.info("Creating task with title: {}", title);

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);

        taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
