package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class UsersController {
    private final UsersService service;

    @GetMapping("/id/{id}")
    public Optional<User> getMusicianById(@PathVariable Long id) {
        return service.findUserById(id);
    }

    @GetMapping("greeting")
    public ModelAndView greeting() {
        return new ModelAndView("greet");
    }

    @GetMapping("signup")
    public ModelAndView signUp() {
        return new ModelAndView("signup");
    }

    @PostMapping("/createUser{login}{email}{firstName}{lastName}{town}{phone}{password}{role}")
    public String createUser(String login,
                             String email,
                             String firstName,
                             String lastName,
                             String town,
                             String phone,
                             String password,
                             String role)
    {
        // если email и логин пользователя есть в БД (true),
        // то возвращется сообщение об ошибке
        if (service.checkExistUser(email, login)) {
            return "Указанная почта или ник уже существует";
        }
        User user = new User();
        user.setId(null);
        user.setNickname(login);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPassword(password);
        // создаем новый город, если его нет в базе
        if (service.getTown(town) == null) {
            Town newTown = new Town();
            newTown.setId(null);
            newTown.setTown(town);
            service.saveTown(newTown);
        }
        // добавляем найденный город к пользователю
        user.setTown(service.getTown(town));

        // добавляем пользователю найденную роль из базы
        user.setRole(service.getRole(role));

        return service.saveOrUpdateUser(user);
    }
}
