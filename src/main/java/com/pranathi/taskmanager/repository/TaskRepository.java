package com.pranathi.taskmanager.repository;

import com.pranathi.taskmanager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Keyword search (pagination)
    Page<Task> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // Filter by status (pagination)
    Page<Task> findByStatus(String status, Pageable pageable);

    // Filter by user (pagination)
    Page<Task> findByUser_Id(Long userId, Pageable pageable);

    // Combined filters: keyword + status + user
    Page<Task> findByTitleContainingIgnoreCaseAndStatusAndUser_Id(
            String title,
            String status,
            Long userId,
            Pageable pageable
    );

    // JOIN + FETCH user for Day 15
    @Query("""
           SELECT t FROM Task t
           JOIN FETCH t.user u
           WHERE u.id = :userId
           """)
    List<Task> findTasksWithUser(@Param("userId") Long userId);
}
