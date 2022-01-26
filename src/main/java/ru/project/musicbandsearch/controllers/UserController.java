package ru.project.musicbandsearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.project.musicbandsearch.dto.AuthRequestDto;
import ru.project.musicbandsearch.dto.AuthResponseDto;
import ru.project.musicbandsearch.services.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto request) {
        return service.login(request);
    }
}
