package org.ucentralasia.org.taskmanagementfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucentralasia.org.taskmanagementfinal.domain.Task;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;
import org.ucentralasia.org.taskmanagementfinal.domain.User;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskRequest;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskResponse;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskPriority;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;
import org.ucentralasia.org.taskmanagementfinal.exception.ResourceNotFoundException;
import org.ucentralasia.org.taskmanagementfinal.repository.TaskRepository;
import org.ucentralasia.org.taskmanagementfinal.repository.TeamRepository;
import org.ucentralasia.org.taskmanagementfinal.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamRepository teamRepository;

    public TaskResponse createTask(String username, TaskRequest request) {
        User user = userService.findByUsername(username);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(TaskPriority.valueOf(request.getPriority().toUpperCase()));
        task.setStatus(TaskStatus.OPEN);
        task.setDueDate(request.getDueDate());
        task.setCreatedBy(user);

        if (request.getTeamId() != null) {
            Team team = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
            task.setAssignedTeam(team);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDto(savedTask);
    }

    public List<TaskResponse> getUserTasks(String username) {
        User user = userService.findByUsername(username);
        List<Task> tasks = taskRepository.findByCreatedById(user.getId());
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<TaskResponse> getTeamTasks(Long teamId) {
        List<Task> tasks = taskRepository.findByAssignedTeamId(teamId);
        return tasks.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return convertToDto(updatedTask);
    }

    private TaskResponse convertToDto(Task task) {
        TaskResponse dto = new TaskResponse();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority());
        dto.setStatus(task.getStatus());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedByUsername(task.getCreatedBy().getUsername());
        dto.setAssignedTeamName(task.getAssignedTeam() != null ? task.getAssignedTeam().getName() : null);
        return dto;
    }
}
