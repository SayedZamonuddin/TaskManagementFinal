package org.ucentralasia.org.taskmanagementfinal.util;

import org.springframework.stereotype.Component;
import org.ucentralasia.org.taskmanagementfinal.dto.TaskResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvExporter {

    public void exportTasks(Writer writer, List<TaskResponse> tasks) throws IOException {
        writer.write("ID,Title,Description,Priority,Status,DueDate,CreatedBy,AssignedTeam\n");
        for (TaskResponse t : tasks) {
            // Replace commas in description to avoid CSV issues
            String description = t.getDescription() != null ? t.getDescription().replace(",", ";") : "";
            String createdBy = t.getCreatedByUsername() != null ? t.getCreatedByUsername() : "";
            String assignedTeam = t.getAssignedTeamName() != null ? t.getAssignedTeamName() : "";
            writer.write(String.format("%d,%s,%s,%s,%s,%s,%s,%s\n",
                    t.getId(),
                    t.getTitle(),
                    description,
                    t.getPriority().name(),
                    t.getStatus().name(),
                    t.getDueDate() != null ? t.getDueDate().toString() : "",
                    createdBy,
                    assignedTeam
            ));
        }
    }
}
