package org.ucentralasia.org.taskmanagementfinal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "teams")
    @JsonBackReference("user-teams")
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "assignedTeam", cascade = CascadeType.ALL)
    @JsonManagedReference("team-tasks")
    private List<Task> tasks = new ArrayList<>();
}
