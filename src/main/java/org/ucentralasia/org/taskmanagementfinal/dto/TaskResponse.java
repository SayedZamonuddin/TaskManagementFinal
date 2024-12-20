package org.ucentralasia.org.taskmanagementfinal.dto;

import lombok.Data;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskPriority;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDateTime dueDate;
    private String createdByUsername;
    private String assignedTeamName;
}
