package ru.project.musicbandsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.*;
import ru.project.musicbandsearch.repositories.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final TownsRepository townsRepository;
    private final RolesRepository rolesRepository;
    private final GenresRepository genresRepository;
    private final InstrumentsRepository instrumentsRepository;

    private Map<String, User> listUsers = new HashMap<>();

    public Optional<User> findUserById(Long id) {
        return usersRepository.findById(id);
    }

    public String getTownById(Long id) {
        return townsRepository.getById(id).getTown();
    }

    public String getGenresById(Long[] id) {
        StringBuilder builder = new StringBuilder();
        List<Genre> genres = genresRepository.getAllById(id);
        for (int i = 1; i < genres.size(); i++) {
            builder.append(genres.get(i));
        }
        return builder.toString();
    }

    public User getUserByNickname(String nickname) {
        if (listUsers.containsKey(nickname)) {
            return listUsers.get(nickname);
        }
        User user = usersRepository.findUserByNickname(nickname);
        listUsers.put(nickname, user);
        return user;
    }

    public User getUserByEmail(String email) {
        if (listUsers.containsKey(email)) {
            return listUsers.get(email);
        }
        Optional<User> user = usersRepository.findUserByEmail(email);
        listUsers.put(email, user.get());
        return user.get();
    }

    // проверка на существующего пользователя в БД
    public boolean checkExistUser(String email, String login) {
        return !usersRepository.findUserByEmail(email).isEmpty() || usersRepository.findUserByNickname(login) != null;
    }

    public String saveOrUpdateUser(User user) {
//        if (usersRepository.findUserByEmail(user.getEmail()) != null || usersRepository.findUserByNickname(user.getNickname()) != null) {
//            return "Указанная почта или ник уже существует";
//        }
        usersRepository.save(user);
        listUsers.put(user.getNickname(), user);
        return "Пользователь " + user.getNickname() + " создан";
    }

    public Boolean loginUserByEmail(String email, String password) {
        System.out.println("SERVICE LOGIN EMAIL: " + email + " " + password);
//        User user = usersRepository.findUserByEmail(email).orElse(null);
        User user = usersRepository.findUserByEmail(email).orElse(null);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    public Boolean loginUserByNickname(String nickname, String password) {
        System.out.println("SERVICE LOGIN NICKNAME: " + nickname + " " + password);
        User user = usersRepository.findUserByNickname(nickname);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        return false;
    }

    /** Получение данных с БД в список **/
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public List<Town> getAllTowns() {
        return townsRepository.findAll();
    }

    public List<Instrument> getAllInstruments() {
        return instrumentsRepository.findAll();
    }

    public List<Genre> getAllGenres() {
        return genresRepository.findAll();
    }
    /****/

    public List<User> getAllUsersByRole(String role) {
        return usersRepository.findUsersByRole_Role(role);
    }

    public List<User> getAllUsersByGenre(String genre) {
        return usersRepository.findUsersByGenre_Genre(genre);
    }

    public List<User> getAll(String role, String genre) {
        return usersRepository.findUsersByRole_RoleAndGenre_Genre(role, genre);
    }

    public Town getTown(String town) {
        return townsRepository.findTownByTown(town);
    }

    public Town saveTown(Town town) {
        return townsRepository.save(town);
    }

    public Instrument getInstrument(String instrument) {
        return instrumentsRepository.findInstrumentByInstrument(instrument);
    }
    public Instrument saveInstrument(Instrument instrument) {
        return instrumentsRepository.save(instrument);
    }
    public Genre getGenre(String genre) {
        return genresRepository.findGenreByGenre(genre);
    }
    public Genre saveGenre(Genre genre) {
        return genresRepository.save(genre);
    }

    public Role getRole(String role) {
        return rolesRepository.findRoleByRole(role);
    }
}
