package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class UsersController {
    private final UsersService service;

    private final PasswordEncoder passwordEncoder;

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

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @GetMapping("login_error")
    public ModelAndView loginError(Model model) {
        model.addAttribute("loginError", "Неверный логин или пароль");
        return new ModelAndView("login");
    }

    @GetMapping("signup")
    public ModelAndView signUp() {
        return new ModelAndView("signup");
    }

    // в разаботке
    @GetMapping("search")
    public ModelAndView search(Model model) {
        List<User> users = service.getAllUsers();
        List<Town> towns = service.getAllTowns();
        model.addAttribute("allTowns", towns);
        model.addAttribute("allUsers", users);
        return new ModelAndView("search");
    }

    @GetMapping("/logout")
    public ModelAndView logout(Authentication authentication) {
        authentication.setAuthenticated(false);
        return new ModelAndView("login");
    }

    @GetMapping("profile")
    public ModelAndView profile(Authentication authentication,
                                Model model) {
        User user = service.getUserByEmail(authentication.getName());
        String login = user.getNickname();
        StringBuilder builder = new StringBuilder();
        model.addAttribute("nickname", login);
        try {
            for (Instrument i : user.getInstrument()) {
                builder.append(i.getInstrument() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены инструменты");
        }
        model.addAttribute("instrument", checkStrBuilder(builder));

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }
        model.addAttribute("genre", checkStrBuilder(builder));
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());
        builder = new StringBuilder();
        builder.append(user.getFirstName());
        try {
            builder.append(" " + user.getLastName());
        } catch (NullPointerException e) {
            System.err.println("У пользователя нет фамилии");
        }
        model.addAttribute("name", builder);
        return new ModelAndView("profile");
    }

    @GetMapping("/profile_edit")
    public ModelAndView profileEdit(Authentication authentication,
                                    Model model) {
        User user = service.getUserByEmail(authentication.getName());
        String login = user.getNickname();
        StringBuilder builder = new StringBuilder();
        model.addAttribute("nickname", login);
        try {
            for (Instrument i : user.getInstrument()) {
                builder.append(i.getInstrument() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены инструменты");
        }
        model.addAttribute("instrument", checkStrBuilder(builder));

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }
        model.addAttribute("genre", checkStrBuilder(builder));
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());

        String role = user.getRole().getRole();
        if (role.equals("musician")) model.addAttribute("roleMusician", true);
        else if (role.equals("band")) model.addAttribute("roleBand", true);
        else if (role.equals("costumer")) model.addAttribute("roleCostumer", true);
        return new ModelAndView("profile_edit");
    }

    // метод убирает последюю запятую у StringBuilder
    private String checkStrBuilder(StringBuilder builder) {
        if (builder.indexOf(", ") != -1) {
            builder.deleteCharAt(builder.length()-2);
        }
        return builder.toString();
    }


    @PostMapping("/profile_edit{firstName}{lastName}{town}{phone}{instrument}{genre}{role}{about}")
    public ModelAndView updateUser(Authentication authentication,
                                   Model model,
                                   String firstName,
                                   String lastName,
                                   String town,
                                   String phone,
                                   String instrument,
                                   String genre,
                                   String role,
                                   String about) {
        User user = service.getUserByEmail(authentication.getName());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        // создаем новый город, если его нет в базе
        if (service.getTown(town) == null) {
            Town newTown = new Town();
            newTown.setId(null);
            newTown.setTown(town);
            service.saveTown(newTown);
        }
        // добавляем найденный город к пользователю
        user.setTown(service.getTown(town));
        user.setPhone(phone);
        // парсим инструменты через запятуню (с запроса)
        if (instrument != null) {
            String[] instruments = instrument.split(", ");
            // создаем массив инструментов
            List<Instrument> instrumentList = new ArrayList<>();
            for (String inst : instruments) {
                if (service.getInstrument(inst) == null) {
                    Instrument newInst = new Instrument();
                    newInst.setId(null);
                    newInst.setInstrument(inst);
                    service.saveInstrument(newInst);
                }
                // добавляем инструменты в массив
                instrumentList.add(service.getInstrument(inst));
            }
            user.setInstrument(instrumentList);
        }

        if (genre != null) {
            String[] genres = genre.split(", ");
            // создаем массив жанров
            List<Genre> genreList = new ArrayList<>();
            for (String gen : genres) {
                if (service.getGenre(gen) == null) {
                    Genre newGenre = new Genre();
                    newGenre.setId(null);
                    newGenre.setGenre(gen);
                    service.saveGenre(newGenre);
                }
                // добавляем жанры в массив
                genreList.add(service.getGenre(gen));
            }
            user.setGenre(genreList);
        }
        // список жанров из массив добавляем пользователю
        user.setRole(service.getRole(role));
        user.setAbout(about);
        service.saveOrUpdateUser(user);


        String login = user.getNickname();
        StringBuilder builder = new StringBuilder();
        model.addAttribute("nickname", login);
        try {
            for (Instrument i : user.getInstrument()) {
                builder.append(i.getInstrument() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены инструменты");
        }
        model.addAttribute("instrument", checkStrBuilder(builder));

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }
        model.addAttribute("genre", checkStrBuilder(builder));
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());
        builder = new StringBuilder();
        builder.append(user.getFirstName());
        try {
            builder.append(" " + user.getLastName());
        } catch (NullPointerException e) {
            System.err.println("У пользователя нет фамилии");
        }
        model.addAttribute("name", builder);
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
                                   Model model) {
        // если email и логин пользователя есть в БД (true),
        // то возвращется сообщение об ошибке
        System.out.println("signup");
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
        user.setPassword(passwordEncoder.encode(password));
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
        model.addAttribute("nick", login);
        return new ModelAndView("signup_ok");
    }

    //@PostMapping("/login{login}{password}")
    public ModelAndView loginUser(String login, String password, Model model) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
