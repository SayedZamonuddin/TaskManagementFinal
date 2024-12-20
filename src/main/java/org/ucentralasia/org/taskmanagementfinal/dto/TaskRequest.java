package org.ucentralasia.org.taskmanagementfinal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private String title;
    private String description;
    private String priority; // "LOW", "MEDIUM", "HIGH"
    private LocalDateTime dueDate;
    private Long teamId; // optional, for assigning to a team
}
