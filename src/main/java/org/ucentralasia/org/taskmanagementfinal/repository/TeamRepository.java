package org.ucentralasia.org.taskmanagementfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ucentralasia.org.taskmanagementfinal.domain.Team;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
