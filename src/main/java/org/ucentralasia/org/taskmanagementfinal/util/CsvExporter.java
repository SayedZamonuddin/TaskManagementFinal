package org.ucentralasia.org.taskmanagementfinal.util;

import org.springframework.stereotype.Component;
import org.ucentralasia.org.taskmanagementfinal.domain.Task;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvExporter {

    public void exportTasks(Writer writer, List<Task> tasks) throws IOException {
        writer.write("ID,Title,Description,Priority,Status,DueDate\n");
        for (Task t : tasks) {
            // Replace commas in description to avoid CSV issues
            String description = t.getDescription() != null ? t.getDescription().replace(",", ";") : "";
            writer.write(String.format("%d,%s,%s,%s,%s,%s\n",
                    t.getId(),
                    t.getTitle(),
                    description,
                    t.getPriority().name(),
                    t.getStatus().name(),
                    t.getDueDate() != null ? t.getDueDate().toString() : ""
            ));
        }
    }
}
