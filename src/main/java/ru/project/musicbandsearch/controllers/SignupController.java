package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class SignupController {

    private final UsersService service;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("signup")
    public ModelAndView signUp() {
        return new ModelAndView("signup");
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
        if (service.checkExistUser(email, login)) {
            model.addAttribute("answer", "Указанный логин или почта уже существует");
            return new ModelAndView("signup");
//            return "Указанная почта или ник уже существует";
        }
        User user = new User();
        user.setId(null);
        user.setAvatar(false);
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
}
