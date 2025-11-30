package com.m01project.taskmanager.sevice;

import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.repository.UserRepository;
import com.m01project.taskmanager.service.UserService;
import com.m01project.taskmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser() {
        User user = new User(1L, "john", "john@gmail.com", "1234");

        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.create(user);

        assertEquals("john", saved.getUsername());
        assertEquals("john@gmail.com", saved.getEmail());
    }

    @Test
    void testGetUserById() {
        User user = new User(1L, "john", "john@gmail.com", "1234");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                new User(1L, "john", "john@gmail.com", "1234"),
                new User(2L, "mike", "mike@gmail.com", "abcd")
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAll();

        assertEquals(2, result.size());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.delete(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}
