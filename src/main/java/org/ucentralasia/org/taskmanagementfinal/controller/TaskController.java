package org.ucentralasia.org.taskmanagementfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskRequest;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskResponse;
import org.ucentralasia.org.taskmanagementfinal.enums.TaskStatus;
import org.ucentralasia.org.taskmanagementfinal.service.TaskService;
import org.ucentralasia.org.taskmanagementfinal.service.UserPrincipal;
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
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @RequestBody TaskRequest request) {
        TaskResponse task = taskService.createTask(userPrincipal.getUsername(), request);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/user")
    public ResponseEntity<List<TaskResponse>> getUserTasks(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<TaskResponse> tasks = taskService.getUserTasks(userPrincipal.getUsername());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TaskResponse>> getTeamTasks(@PathVariable Long teamId) {
        List<TaskResponse> tasks = taskService.getTeamTasks(teamId);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{taskId}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Long taskId,
                                                     @RequestParam String status,
                                                     @AuthenticationPrincipal UserPrincipal userPrincipal) {
        TaskResponse task = taskService.updateTaskStatus(taskId, TaskStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(task);
    }

    @GetMapping("/export/user")
    public void exportUserTasks(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                HttpServletResponse response) throws IOException {
        List<TaskResponse> tasks = taskService.getUserTasks(userPrincipal.getUsername());
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=user_tasks.csv");
        csvExporter.exportTasks(response.getWriter(), tasks);
    }

    @GetMapping("/export/team/{teamId}")
    public void exportTeamTasks(@PathVariable Long teamId,
                                HttpServletResponse response,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) throws IOException {
        List<TaskResponse> tasks = taskService.getTeamTasks(teamId);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=team_tasks.csv");
        csvExporter.exportTasks(response.getWriter(), tasks);
    }
}
