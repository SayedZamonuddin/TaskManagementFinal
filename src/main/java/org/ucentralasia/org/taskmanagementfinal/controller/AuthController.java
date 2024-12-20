package org.ucentralasia.org.taskmanagementfinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.ucentralasia.org.taskmanagementfinal.domain.User;
import org.ucentralasia.org.taskmanagementfinal.dto.UserRegistrationRequest;
import org.ucentralasia.org.taskmanagementfinal.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }
}
