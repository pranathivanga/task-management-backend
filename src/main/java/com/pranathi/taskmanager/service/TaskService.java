package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.entity.Task;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.exception.AccessDeniedException;
import com.pranathi.taskmanager.exception.ResourceNotFoundException;
import com.pranathi.taskmanager.repository.TaskRepository;
import com.pranathi.taskmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    // 🔐 CORE HELPER — GET LOGGED-IN USER
    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    // ✅ CREATE TASK — USER OWNERSHIP ENFORCED
    @Transactional
    public Task createTask(
            String title,
            String description,
            String status,
            String idempotencyKey
    ) {

        // Idempotency protection
        taskRepository.findByIdempotencyKey(idempotencyKey)
                .ifPresent(existing -> {
                    throw new RuntimeException("Duplicate request blocked");
                });

        // 🔥 THIS IS THE IMPORTANT PART YOU ASKED ABOUT
        User currentUser = getCurrentUser();
        // why? → backend decides ownership, not client

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setUser(currentUser);          // ✅ OWNER SET HERE
        task.setIdempotencyKey(idempotencyKey);

        return taskRepository.save(task);
    }

    // ✅ GET ONLY LOGGED-IN USER'S TASKS
    public List<Task> getMyTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }

    // ⚠️ UPDATE TASK (ownership check comes Day 13)
    @Transactional
    public Task updateTask(Long taskId, String status, String description) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        User currentUser = getCurrentUser();

        // 🔐 AUTHORIZATION CHECK
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to update this task");
        }

        task.setStatus(status);
        if (description != null) {
            task.setDescription(description);
        }

        return taskRepository.save(task);
    }


    // ⚠️ DELETE TASK (ownership check comes Day 13)
    @Transactional
    public void deleteTask(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        User currentUser = getCurrentUser();

        // 🔐 AUTHORIZATION CHECK
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to delete this task");
        }

        taskRepository.delete(task);
    }

}