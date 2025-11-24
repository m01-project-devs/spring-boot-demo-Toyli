package com.m01project.taskmanager.sevice;

import com.m01project.taskmanager.model.User;
import com.m01project.taskmanager.repository.UserRepository;
import com.m01project.taskmanager.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repo;
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        repo = mock(UserRepository.class);
        service = new UserServiceImpl(repo);
    }

    @Test
    void createUser_savesAndReturnsSavedUser() {
        // prepare
        User toSave = new User();
        toSave.setEmail("create@example.com");
        toSave.setUsername("createUser");
        toSave.setPassword("pass");

        User saved = new User();
        saved.setId(10L);
        saved.setEmail("create@example.com");
        saved.setUsername("createUser");
        saved.setPassword("pass");

        when(repo.save(any(User.class))).thenReturn(saved);

        // act
        User result = service.create(toSave);

        // assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getEmail()).isEqualTo("create@example.com");

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("create@example.com");
    }

    @Test
    void getById_whenExists_returnsOptionalWithUser() {
        User u = new User();
        u.setId(5L);
        u.setEmail("g@e.com");

        when(repo.findById(5L)).thenReturn(Optional.of(u));

        Optional<User> opt = service.getById(5L);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(5L);
        assertThat(opt.get().getEmail()).isEqualTo("g@e.com");
    }

    @Test
    void getAll_returnsAllUsers() {
        User a = new User(); a.setId(1L); a.setEmail("a@a.com");
        User b = new User(); b.setId(2L); b.setEmail("b@b.com");

        when(repo.findAll()).thenReturn(List.of(a, b));

        List<User> list = service.getAll();

        assertThat(list).hasSize(2);
        assertThat(list).extracting(User::getId).containsExactly(1L, 2L);
    }

    @Test
    void update_existingUser_updatesFieldsAndReturnsUpdated() {
        // existing in DB
        User existing = new User();
        existing.setId(3L);
        existing.setUsername("old");
        existing.setEmail("old@example.com");
        existing.setPassword("oldpass");

        // incoming update payload
        User incoming = new User();
        incoming.setUsername("new");
        incoming.setEmail("new@example.com");
        incoming.setPassword("newpass");

        when(repo.findById(3L)).thenReturn(Optional.of(existing));
        when(repo.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User updated = service.update(3L, incoming);

        assertThat(updated.getId()).isEqualTo(3L);
        assertThat(updated.getUsername()).isEqualTo("new");
        assertThat(updated.getEmail()).isEqualTo("new@example.com");
        // fixed expected password -> "newpass"
        assertThat(updated.getPassword()).isEqualTo("newpass");

        verify(repo).findById(3L);
        verify(repo).save(existing);
    }

    @Test
    void update_whenNotFound_throwsNoSuchElementException() {
        User incoming = new User();
        incoming.setEmail("x@x.com");

        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(99L, incoming))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("User not found");

        verify(repo).findById(99L);
        verify(repo, never()).save(any());
    }

    @Test
    void delete_callsRepositoryDeleteById() {
        // default behavior: do nothing on delete
        doNothing().when(repo).deleteById(4L);

        service.delete(4L);

        verify(repo).deleteById(4L);
    }
}
