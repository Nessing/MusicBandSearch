package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

import javax.sql.DataSource;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class UsersController {
    private final UsersService service;

    private final DataSource dataSource;

    @GetMapping("/admin")
    public String admin() {
        return "Admin";
    }

    @GetMapping("/index")
    public ModelAndView index() {

        return new ModelAndView("index");
    }

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

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        System.out.println("51.. LOGOUT");
        return new ModelAndView("login");
    }

    @GetMapping("profile")
    public ModelAndView profile(Authentication authentication,
                                Model model,
                                String email,
                                String instrument,
                                String genre,
                                String town,
                                String phone,
                                String about) {
        String login = authentication.getName();
        User user = service.getUserByNickname(login);
        System.out.println("66.. login: " + login);
        StringBuilder builder = new StringBuilder();
        model.addAttribute("nickname", login);
        try {
            for (Instrument i : user.getInstrument()) {
                builder.append(i.getInstrument() + " ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены инструменты");
        }
        model.addAttribute("instrument", builder);

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + " ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }
        model.addAttribute("genre", builder);
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());
        return new ModelAndView("profile");
    }

    @PostMapping("/signup{login}{email}{firstName}{lastName}{town}{phone}{password}{role}")
    public ModelAndView createUser(String login,
                                   String email,
                                   String firstName,
                                   String lastName,
                                   String town,
                                   String phone,
                                   String password,
                                   String role,
                                   Model model)
    {
        // если email и логин пользователя есть в БД (true),
        // то возвращется сообщение об ошибке
        if (service.checkExistUser(email, login)) {
            model.addAttribute("answer", "Указанный логин или почта уже существует");
            return new ModelAndView("signup");
//            return "Указанная почта или ник уже существует";
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

        service.saveOrUpdateUser(user);
        System.out.println("138.. " + user);
        model.addAttribute("nick", login);
        return new ModelAndView("signup_ok");
    }

    @PostMapping("/login{login}{password}")
    public ModelAndView loginUser(String login, String password, Model model) {
        System.out.println("146.. LOGIN");
        // проверка на заполнение всех полей
        if (login == null || password == null) {
            model.addAttribute("loginError", "значения не введены");
            return new ModelAndView("login");
        }
        // если в логине содержиться "@", то искать пользователя по почте
        if (login.contains("@")) {
            if (service.loginUserByNickname(login, password)) {
            } else {
                model.addAttribute("loginError", "Неверный логин или пароль");
                return new ModelAndView("login"); // страница входа с сообщением ошибки
            }
            // в ином случае искать пользователя по логину
        } else {
            if (service.loginUserByNickname(login, password)) {
            } else {
                model.addAttribute("loginError", "Неверный логин или пароль");
                return new ModelAndView("login"); // страница входа с сообщением ошибки
            }
        }
        // возвращается страница профиля
        User user = service.getUserByNickname(login);
        StringBuilder builder = new StringBuilder();
        model.addAttribute("nickname", login);
        try {
            for (Instrument i : user.getInstrument()) {
                builder.append(i.getInstrument() + " ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены инструменты");
        }
        model.addAttribute("instrument", builder);

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + " ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }
        model.addAttribute("genre", builder);
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());
        return new ModelAndView("profile");
    }
}