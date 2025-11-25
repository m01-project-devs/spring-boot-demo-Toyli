package com.m01project.taskmanager.service;

import com.m01project.taskmanager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);
    Optional<User> getById(Long id);
    List<User> getAll();
    User update(Long id, User user);
    void delete(String email);
}
