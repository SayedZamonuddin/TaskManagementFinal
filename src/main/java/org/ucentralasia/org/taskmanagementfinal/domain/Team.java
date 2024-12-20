package org.ucentralasia.org.taskmanagementfinal.domain;

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

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "teams")
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "assignedTeam", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
}
