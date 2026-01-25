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

    // Keyword filter
    Page<Task> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    // Status filter
    Page<Task> findByStatus(String status, Pageable pageable);

    // User filter
    Page<Task> findByUser_Id(Long userId, Pageable pageable);

    // Combined filters
    Page<Task> findByTitleContainingIgnoreCaseAndStatusAndUser_Id(
            String title,
            String status,
            Long userId,
            Pageable pageable
    );

    // Day 15 — Fetch tasks with user for specific user
    @Query("""
       SELECT t FROM Task t
       JOIN FETCH t.user u
       WHERE u.id = :userId
       """)
    List<Task> findTasksWithUser(@Param("userId") Long userId);

    // Day 19 — Fetch all tasks with user (avoid N+1)
    @Query(
            value = """
            SELECT t FROM Task t
            JOIN FETCH t.user
        """,
            countQuery = "SELECT COUNT(t) FROM Task t"
    )
    Page<Task> findTasksWithUser(Pageable pageable);
}
