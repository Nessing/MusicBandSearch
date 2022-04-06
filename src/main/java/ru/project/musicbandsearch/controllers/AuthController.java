package ru.project.musicbandsearch.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "api/v1")
public class AuthController {

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        return new ModelAndView("login");
    }

    @GetMapping("login_error")
    public ModelAndView loginError(Model model) {
        model.addAttribute("loginError", "Неверный логин или пароль");
        return new ModelAndView("login");
    }
}
