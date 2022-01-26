package ru.project.musicbandsearch.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.project.musicbandsearch.entities.UserDto;
import ru.project.musicbandsearch.repositories.UserSpecifications;
import ru.project.musicbandsearch.services.UserService;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public Page<UserDto> findAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }

        return userService.findAll(UserSpecifications.build(params), page, 4);
    }

    @GetMapping("/{id}")
    public UserDto findProductById(@PathVariable Long id) {
        return userService.findUserDtoById(id).get();
    }

    @GetMapping("/ids")
    public List<UserDto> findProductById(@RequestParam List<Long> ids) {
        return userService.findUserDtosByIds(ids);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveNewProduct(@RequestBody UserDto product) throws ParseException {
        return userService.saveOrUpdate(product);
    }

    @PutMapping
    public UserDto updateProduct(@RequestBody UserDto product) throws ParseException {
        return userService.saveOrUpdate(product);
    }

    @DeleteMapping("/{id}")
    public void updateProduct(@PathVariable Long id) {
        userService.deleteById(id);
    }
}