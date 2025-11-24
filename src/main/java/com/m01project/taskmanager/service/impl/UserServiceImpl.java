package com.m01project.taskmanager.service.impl;

import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.repository.UserRepository;
import com.m01project.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        // ID should be null so JPA creates a new row
        user.setId(null);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(Long id, User user) {
        // sunny-day: assume user exists -> throw if not
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));

        // Update allowed fields
        existing.setEmail(user.getEmail());
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());

        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
