package com.pranathi.taskmanager.repository;

import com.pranathi.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
