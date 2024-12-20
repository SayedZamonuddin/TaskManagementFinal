package org.ucentralasia.org.taskmanagementfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.ucentralasia.org.taskmanagementfinal.domain.Task;
import org.ucentralasia.org.taskmanagementfinal.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private TaskRepository taskRepository;

    // This runs every day at midnight
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDueDateRemindersAndOverdueNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime soon = now.plusDays(1);

        List<Task> dueSoonTasks = taskRepository.findTasksDueSoon(now, soon);
        for (Task t : dueSoonTasks) {
            // In a real scenario, send an email or notification
            System.out.println("Reminder: Task " + t.getId() + " ("+ t.getTitle() + ") is due soon!");
        }

        List<Task> overdueTasks = taskRepository.findOverdueTasks(now);
        for (Task t : overdueTasks) {
            // In a real scenario, send an email or notification
            System.out.println("Overdue: Task " + t.getId() + " ("+ t.getTitle() + ") is overdue!");
        }
    }
}
