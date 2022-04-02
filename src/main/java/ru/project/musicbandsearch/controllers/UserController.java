package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.methods.Checks;
import ru.project.musicbandsearch.services.UsersService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class UserController {

    private final UsersService service;

    private Checks checks = new Checks();

//    // провера наличия аватарки у пользователя
//    private void checkAvatar(Model model, User user) {
//        String path;
//        if (user.getAvatar()) {
//            path = "avatar/avatar.jpg";
//        } else { //дефолтная картинка на аву
//            path = "/img/profile/defaultpic.png";
//        }
//        model.addAttribute("avatar", path);
//    }

    @GetMapping("users/{id}")
    public ModelAndView getUser(@PathVariable Long id,
                                Model model) {
        User user = service.findUserById(Long.valueOf(id)).get();
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
        if (user.getRole().getRole().equals("musician")) {
            model.addAttribute("roleInstrument", "владеет инструментами:  ");
        } else {
            model.addAttribute("roleInstrument", "ищет музыкантов:  ");
        }
        model.addAttribute("instrument", checks.checkStrBuilder(builder));

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }

        model.addAttribute("genre", checks.checkStrBuilder(builder));
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());
        model.addAttribute("id", user.getId() + "/");
        builder = new StringBuilder();
        builder.append(user.getFirstName());
        try {
            builder.append(" " + user.getLastName());
        } catch (NullPointerException e) {
            System.err.println("У пользователя нет фамилии");
        }

        checks.checkAvatar(model, user);
        checks.changeRoleRus(model, user);

        model.addAttribute("name", builder);
        System.out.println(user.getAvatar());
        return new ModelAndView("user");
    }

//    // метод изменяет название роли
//    private void changeRoleRus(Model model, User user) {
//        if (user.getRole().getRole().equals("musician")) model.addAttribute("role", "музыкант");
//        else if (user.getRole().getRole().equals("band")) model.addAttribute("role", "группа");
//        else if (user.getRole().getRole().equals("costumer")) model.addAttribute("role", "заказчик");
//    }
//
//    // метод убирает последюю запятую у StringBuilder
//    private String checkStrBuilder(StringBuilder builder) {
//        if (builder.indexOf(", ") != -1) {
//            builder.deleteCharAt(builder.length() - 2);
//        }
//        return builder.toString();
//    }
}
