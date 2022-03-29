package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final UsersService service;

//    @GetMapping("profile")
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

        changeRoleRus(model, user);
        checkAvatar(model, user);

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

    private void checkAvatar(Model model, User user) {
        String path;
        if (user.getAvatar()) {
            path = "/users/" + user.getId() + "/avatar/avatar_" + user.getId() + ".jpg";
        } else { //дефолтная картинка на аву
            path = "templates/profile/defaultpic.png";
        }
        model.addAttribute("avatar", path);
    }

    // метод изменяет название роли
    private void changeRoleRus(Model model, User user) {
        if (user.getRole().getRole().equals("musician")) model.addAttribute("role", "музыкант");
        else if (user.getRole().getRole().equals("band")) model.addAttribute("role", "группа");
        else if (user.getRole().getRole().equals("costumer")) model.addAttribute("role", "заказчик");
    }

    // метод убирает последюю запятую у StringBuilder
    private String checkStrBuilder(StringBuilder builder) {
        if (builder.indexOf(", ") != -1) {
            builder.deleteCharAt(builder.length() - 2);
        }
        return builder.toString();
    }
}
