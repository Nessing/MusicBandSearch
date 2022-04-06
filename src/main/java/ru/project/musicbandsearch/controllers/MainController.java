package ru.project.musicbandsearch.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "api/v1")
public class MainController {

    @GetMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

//    @GetMapping("/id/{id}")
//    public Optional<User> getMusicianById(@PathVariable Long id) {
//        return service.findUserById(id);
//    }

}
