package ru.project.musicbandsearch.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AuthController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        return new ModelAndView("login");
    }
}
