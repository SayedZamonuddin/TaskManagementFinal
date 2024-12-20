package org.ucentralasia.org.taskmanagementfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucentralasia.org.taskmanagementfinal.domain.Task;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;
import org.ucentralasia.org.taskmanagementfinal.domain.User;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskRequest;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskPriority;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;
import org.ucentralasia.org.taskmanagementfinal.exception.ResourceNotFoundException;
import org.ucentralasia.org.taskmanagementfinal.repository.TaskRepository;
import org.ucentralasia.org.taskmanagementfinal.repository.TeamRepository;
import org.ucentralasia.org.taskmanagementfinal.repository.UserRepository;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamRepository teamRepository;

    public Task createTask(String username, TaskRequest request) {
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

        return taskRepository.save(task);
    }

    public List<Task> getUserTasks(String username) {
        User user = userService.findByUsername(username);
        return taskRepository.findByCreatedById(user.getId());
    }

    public List<Task> getTeamTasks(Long teamId) {
        return taskRepository.findByAssignedTeamId(teamId);
    }

    public Task updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}
