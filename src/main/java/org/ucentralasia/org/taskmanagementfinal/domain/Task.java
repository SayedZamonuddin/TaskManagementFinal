package org.ucentralasia.org.taskmanagementfinal.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskPriority;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_team_id")
    private Team assignedTeam;
}
