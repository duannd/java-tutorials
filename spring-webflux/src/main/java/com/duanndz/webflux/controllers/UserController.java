package com.duanndz.webflux.controllers;

import com.duanndz.webflux.models.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<Long, User> USERS = new ConcurrentHashMap<>();

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        Long id = (long) USERS.size() + 1;
        user.setId(id);
        USERS.put(id, user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        if (user.getId() == null || USERS.get(user.getId()) == null) {
            throw new RuntimeException("Not found user");
        }
        return USERS.put(user.getId(), user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(USERS.values());
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return USERS.get(id);
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        return USERS.remove(id);
    }

}
