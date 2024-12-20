package org.ucentralasia.org.taskmanagementfinal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucentralasia.org.taskmanagementfinal.domain.User;
import org.ucentralasia.org.taskmanagementfinal.dto.UserRegistrationRequest;
import org.ucentralasia.org.taskmanagementfinal.exception.BadRequestException;
import org.ucentralasia.org.taskmanagementfinal.exception.ResourceNotFoundException;
import org.ucentralasia.org.taskmanagementfinal.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserRegistrationRequest request) {
        Optional<User> existing = userRepository.findByUsername(request.getUsername());
        if (existing.isPresent()) {
            throw new BadRequestException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // In production: encode password
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
