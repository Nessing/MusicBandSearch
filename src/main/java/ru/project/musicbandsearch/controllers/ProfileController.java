package ru.project.musicbandsearch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.project.musicbandsearch.configurations.services.FilesStorageService;
import ru.project.musicbandsearch.entities.Genre;
import ru.project.musicbandsearch.entities.Instrument;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.methods.Checks;
import ru.project.musicbandsearch.services.UsersService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/v1")
public class ProfileController {

    private final UsersService service;

    private final FilesStorageService storageService;

    private final Checks checks;

    @GetMapping("profile")
    public ModelAndView getProfile(Authentication authentication,
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
        model.addAttribute("instrument", checks.checkStrBuilder(builder));

        builder = new StringBuilder();
        try {
            for (Genre g : user.getGenre()) {
                builder.append(g.getGenre() + ", ");
            }
        } catch (NullPointerException e) {
            System.err.println("У пользователя не установлены жанры");
        }

        checks.changeRoleRus(model, user);
        checks.checkAvatar(model, user);

        model.addAttribute("genre", checks.checkStrBuilder(builder));
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
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("town", user.getTown().getTown());
        model.addAttribute("phone", user.getPhone());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("about", user.getAbout());

        checks.checkAvatar(model, user);

        String role = user.getRole().getRole();
        if (role.equals("musician")) model.addAttribute("roleMusician", true);
        else if (role.equals("band")) model.addAttribute("roleBand", true);
        else if (role.equals("costumer")) model.addAttribute("roleCostumer", true);
        return new ModelAndView("profile_edit");
    }

    @PostMapping("/profile_edit{photo}{firstName}{lastName}{town}{phone}{instrument}{genre}{role}{about}")
    public ModelAndView updateProfile(@RequestParam("photo") MultipartFile photo,
                                      Authentication authentication,
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
                // создаем новый инструмент, если его нет
                if (service.getInstrument(inst.strip()) == null) {
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
            String[] genres = genre.strip().split(", ");
            // создаем массив жанров
            List<Genre> genreList = new ArrayList<>();
            for (String gen : genres) {
                // добавляем новый жанр, если его нет
                if (service.getGenre(gen.strip()) == null) {
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

        String message = "";
        try {
            if (!photo.isEmpty()) {
                storageService.save(photo, user.getId());
                user.setAvatar(true);
            }
            message = "Uploaded the file successfully: " + photo.getOriginalFilename();
        } catch (Exception e) {
            message = "Could not upload the file: " + photo.getOriginalFilename() + "!";
        }

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
        builder = new StringBuilder();
        builder.append(user.getFirstName());
        try {
            builder.append(" " + user.getLastName());
        } catch (NullPointerException e) {
            System.err.println("У пользователя нет фамилии");
        }

        checks.checkAvatar(model, user);

        model.addAttribute("name", builder);
        return new ModelAndView("profile");
    }
}
