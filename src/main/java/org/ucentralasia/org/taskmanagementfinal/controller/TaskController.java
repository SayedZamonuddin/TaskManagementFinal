package org.ucentralasia.org.taskmanagementfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ucentralasia.org.taskmanagementfinal.domain.Task;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskRequest;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;
import org.ucentralasia.org.taskmanagementfinal.service.TaskService;
import org.ucentralasia.org.taskmanagementfinal.util.CsvExporter;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private CsvExporter csvExporter;

    @PostMapping
    public Task createTask(@RequestParam String username, @RequestBody TaskRequest request) {
        return taskService.createTask(username, request);
    }

    @GetMapping("/user")
    public List<Task> getUserTasks(@RequestParam String username) {
        return taskService.getUserTasks(username);
    }

    @GetMapping("/team/{teamId}")
    public List<Task> getTeamTasks(@PathVariable Long teamId) {
        return taskService.getTeamTasks(teamId);
    }

    @PutMapping("/{taskId}/status")
    public Task updateStatus(@PathVariable Long taskId, @RequestParam String status) {
        return taskService.updateTaskStatus(taskId, TaskStatus.valueOf(status.toUpperCase()));
    }

    @GetMapping("/export/user")
    public void exportUserTasks(@RequestParam String username, HttpServletResponse response) throws IOException {
        List<Task> tasks = taskService.getUserTasks(username);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=user_tasks.csv");
        csvExporter.exportTasks(response.getWriter(), tasks);
    }

    @GetMapping("/export/team/{teamId}")
    public void exportTeamTasks(@PathVariable Long teamId, HttpServletResponse response) throws IOException {
        List<Task> tasks = taskService.getTeamTasks(teamId);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=team_tasks.csv");
        csvExporter.exportTasks(response.getWriter(), tasks);
    }
}
