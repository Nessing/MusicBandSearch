package ru.project.musicbandsearch.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getOneUserById(@PathVariable Long id) {
        return userService.findById(id).get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User saveNewUser(@RequestBody User user) {
        return userService.save(user);
    }

}