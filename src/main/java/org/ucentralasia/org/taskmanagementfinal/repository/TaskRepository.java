package org.ucentralasia.org.taskmanagementfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.ucentralasia.org.taskmanagementfinal.domain.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTeamId(Long teamId);

    List<Task> findByCreatedById(Long userId);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :now AND t.status <> 'DONE'")
    List<Task> findOverdueTasks(@Param("now") LocalDateTime now);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :now AND :deadline AND t.status <> 'DONE'")
    List<Task> findTasksDueSoon(@Param("now") LocalDateTime now, @Param("deadline") LocalDateTime deadline);
}
