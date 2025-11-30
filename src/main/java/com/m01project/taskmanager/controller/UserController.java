package com.m01project.taskmanager.controller;

import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.create(user);
    }

    @GetMapping("/{email}")
    public User getUser(@PathVariable Long email) {
        return userService.getById(email).orElse(null);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/{email}")
    public User updateUser(@PathVariable Long email, @RequestBody User user) {
        return userService.update(email, user);
    }

    @DeleteMapping("/{email}")
    public void deleteUser(@PathVariable Long email) {
        userService.delete(email);
    }
}
