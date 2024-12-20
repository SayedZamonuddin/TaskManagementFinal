package org.ucentralasia.org.taskmanagementfinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ucentralasia.org.taskmanagementfinal.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
