package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.exception.ResourceNotFoundException;
import com.pranathi.taskmanager.repository.TaskRepository;
import com.pranathi.taskmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Transactional
    public Task createTask(String title, String description, String status, Long userId, String idempotencyKey) {

        taskRepository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(existing -> {
                    throw new RuntimeException("Duplicate request blocked");
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setUser(user);
        task.setIdempotencyKey(idempotencyKey);

        return taskRepository.save(task);
    }

    public Page<Task> getAllTasks(
            String keyword,
            String status,
            Long userId,
            Pageable pageable) {

        boolean hasKeyword = keyword != null && !keyword.isBlank();
        boolean hasStatus = status != null && !status.isBlank();
        boolean hasUser = userId != null;

        // All three filters
        if (hasKeyword && hasStatus && hasUser) {
            return taskRepository
                    .findByTitleContainingIgnoreCaseAndStatusAndUser_Id(
                            keyword, status, userId, pageable);
        }

        // Keyword only
        if (hasKeyword) {
            return taskRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }

        // Status only
        if (hasStatus) {
            return taskRepository.findByStatus(status, pageable);
        }

        // User only
        if (hasUser) {
            return taskRepository.findByUser_Id(userId, pageable);
        }

        // No filters
        return taskRepository.findTasksWithUser(pageable);

    }
@Transactional
    public Task updateTask(Long taskId, String status, String description) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setStatus(status);

        if (description != null) {
            task.setDescription(description);
        }

        return taskRepository.save(task);
    }
    @Transactional
    public void deleteTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }
    public List<Task> getTasksWithUser(Long userId) {
        return taskRepository.findTasksWithUser(userId);
    }

}
