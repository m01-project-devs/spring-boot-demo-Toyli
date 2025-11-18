package com.m01project.taskmanager.service;

import com.m01project.taskmanager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    /**
     * Create a new user.
     */
    User create(User user);

    /**
     * Read a user by id.
     */
    Optional<User> getById(Long id);

    /**
     * Read all users.
     */
    List<User> getAll();

    /**
     * Update a user with the given id (sunny-day: assume user exists).
     */
    User update(Long id, User user);

    /**
     * Delete a user by id.
     */
    void delete(Long id);
}
