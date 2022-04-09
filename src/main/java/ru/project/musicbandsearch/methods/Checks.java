package ru.project.musicbandsearch.methods;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.project.musicbandsearch.entities.User;

@Component
public class Checks {
    // провера наличия аватарки у пользователя
    public void checkAvatar(Model model, User user) {
        String path;
        if (user.getAvatar()) {
            path = "avatar/avatar.jpg";
        } else { //дефолтная картинка на аву
            path = "/img/profile/defaultpic.png";
        }
        model.addAttribute("avatar", path);
    }

    // метод изменяет название роли
    public void changeRoleRus(Model model, User user) {
        if (user.getRole().getRole().equals("musician")) model.addAttribute("role", "музыкант");
        else if (user.getRole().getRole().equals("band")) model.addAttribute("role", "группа");
        else if (user.getRole().getRole().equals("costumer")) model.addAttribute("role", "заказчик");
    }

    // метод убирает последюю запятую у StringBuilder
    public String checkStrBuilder(StringBuilder builder) {
        if (builder.indexOf(", ") != -1) {
            builder.deleteCharAt(builder.length() - 2);
        }
        return builder.toString();
    }
}
