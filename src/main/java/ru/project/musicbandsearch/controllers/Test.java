package ru.project.musicbandsearch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.project.musicbandsearch.entity.Group;
import ru.project.musicbandsearch.entity.Musician;
import ru.project.musicbandsearch.repositories.GroupRepository;
import ru.project.musicbandsearch.repositories.MusicianRepository;

import java.util.List;

@RestController
public class Test {
    @Autowired
    private GroupRepository repository;
    @GetMapping("/test")
    public String test(){
        List<Group> all = repository.findAll();
        all.forEach(musician -> System.out.println(musician.getId() + " " + musician.getMusicians().toString() + " " + musician.getPrice()));
        return "OK";
    }
}
