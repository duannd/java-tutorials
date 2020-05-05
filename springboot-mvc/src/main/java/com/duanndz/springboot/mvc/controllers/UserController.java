package com.duanndz.springboot.mvc.controllers;

import com.duanndz.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * duan.nguyen
 * Datetime 5/5/20 09:56
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Map<Integer, User> USER_MAP = new ConcurrentHashMap<>();

    // Create User
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody @Valid User user) {
        log.info("Add new User {}", user);
        user.setId(USER_MAP.size() + 1);
        USER_MAP.put(user.getId(), user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        log.info("Get all users");
        return ResponseEntity.ok(new ArrayList<>(USER_MAP.values()));
    }

    // Read
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        log.info("Get User by ID: {}", id);
        return ResponseEntity.ok(USER_MAP.get(id));
    }

    // Update
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid User user) {
        log.info("Update User: {}", user);
        User oldUser = USER_MAP.get(user.getId());
        if (oldUser == null) {
            return ResponseEntity.notFound().build();
        }
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        return ResponseEntity.ok(oldUser);
    }

    // Delete
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        log.info("Delete User of {}", id);
        USER_MAP.remove(id);
        return ResponseEntity.noContent().build();
    }

}
