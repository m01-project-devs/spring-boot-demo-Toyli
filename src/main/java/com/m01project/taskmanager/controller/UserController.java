package com.m01project.taskmanager.controller;

import com.m01project.taskmanager.dto.UserResponseDto;
import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseEntity.status(201).body(new UserResponseDto(created.getId(), created.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return userService.getById(id)
                .map(u -> new UserResponseDto(u.getId(), u.getEmail()))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(u -> new UserResponseDto(u.getId(), u.getEmail()))
                .toList();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.update(id, user);
        return ResponseEntity.ok(new UserResponseDto(updated.getId(), updated.getEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
