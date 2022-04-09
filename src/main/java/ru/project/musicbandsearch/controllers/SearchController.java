package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.services.UsersService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class SearchController {

    private final UsersService service;

    // метод для получения данных из БД в списки для формы поиска
    private Model getFormSearchInputs(Model model) {
        List<User> users = service.getAllUsers();
        List<Town> towns = service.getAllTowns();
        List<Instrument> instruments = service.getAllInstruments();
        List<Genre> genres = service.getAllGenres();
        model.addAttribute("allTowns", towns);
        model.addAttribute("allUsers", users);
        model.addAttribute("allInstruments", instruments);
        model.addAttribute("allGenres", genres);
        return model;
    }

    @GetMapping("search")
    public ModelAndView search(Model model) {
        getFormSearchInputs(model);
        model.addAttribute("displayBlock", "display: none");
        return new ModelAndView("search");
    }

    @PostMapping("/search{nicknameSearch}{role}{genre}{instrument}{town}")
    public ModelAndView search(Model model,
                               String nicknameSearch,
                               String role,
                               String genre,
                               String instrument,
                               String town) {
        if (nicknameSearch != "") {
            User user = service.getUserByNickname(nicknameSearch);
            if (user != null) {
                model.addAttribute("userId", user.getId());
                model.addAttribute("displayBlock", "display: block");
                model.addAttribute("nicknameSearch", user.getNickname());
                model.addAttribute("role", user.getRole().getRole());
                model.addAttribute("genres", user.getGenre());
                model.addAttribute("instruments", user.getInstrument());
                model.addAttribute("town", user.getTown().getTown());
            } else {
                model.addAttribute("displayBlock", "display: none");
                model.addAttribute("errorSearch", "пользователь с таким ником не найден");
            }
        } else {
            model.addAttribute("displayBlock", "display: none");
            List<User> usersByRole = service.getAllUsersByRole(role);
            Genre genreContains = service.getGenre(genre);
            Instrument instrumentContains = service.getInstrument(instrument);
            Town townContains = service.getTown(town);
            // если выбран жанр
            if (genre != "") {
                if (usersByRole.size() > 0) {
                    for (int i = 0; i < usersByRole.size(); i++) {
                        // у каждого пользователя проверяем наличие выбранного жанра
                        if (!usersByRole.get(i).getGenre().contains(genreContains)) {
                            usersByRole.remove(i); // если нет соответствия, то удаляем из списка
                            i--; // после удаления переводим курсор на -1 (остаемся на текущей позиции)
                        }
                        // если массив пустой, тогда выходим из цикла
                        if (usersByRole.size() <= 0) {
                            break;
                        }
                    }
                }
            }
            // если выбран инструмент
            if (instrument != "") {
                if (usersByRole.size() > 0) {
                    for (int i = 0; i < usersByRole.size(); i++) {
                        // у каждого пользователя проверяем наличие выбранного инструмента
                        if (!usersByRole.get(i).getInstrument().contains(instrumentContains)) {
                            usersByRole.remove(i); // если нет соответствия, то удаляем из списка
                            i--; // после удаления переводим курсор на -1 (остаемся на текущей позиции)
                        }
                        // если массив пустой, тогда выходим из цикла
                        if (usersByRole.size() <= 0) {
                            break;
                        }
                    }
                }
            }
            // если выбран город
            if (town != "") {
                if (usersByRole.size() > 0) {
                    for (int i = 0; i < usersByRole.size(); i++) {
                        // у каждого пользователя проверяем на совпадение выбранного города
                        if (!usersByRole.get(i).getTown().equals(townContains)) {
                            usersByRole.remove(i); // если нет соответствия, то удаляем из списка
                            i--; // после удаления переводим курсор на -1 (остаемся на текущей позиции)
                        }
                        // если массив пустой, тогда выходим из цикла
                        if (usersByRole.size() <= 0) {
                            break;
                        }
                    }
                }
            }
            model.addAttribute("usersRole", usersByRole);
        }
        getFormSearchInputs(model);
        return new ModelAndView("search");
    }
}
