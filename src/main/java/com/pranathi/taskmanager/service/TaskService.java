package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.repository.TaskRepository;
import com.pranathi.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(String title, String description, Long userId) {

        logger.info("Creating task with title: {} for user {}", title, userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus("PENDING");
        task.setUser(user);

        return taskRepository.save(task);
    }

    public Page<Task> getAllTasks(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return taskRepository.findAll(pageable);
        }
        return taskRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    }
}
