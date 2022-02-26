package ru.project.musicbandsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.Role;
import ru.project.musicbandsearch.entities.Town;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.repositories.RolesRepository;
import ru.project.musicbandsearch.repositories.TownsRepository;
import ru.project.musicbandsearch.repositories.UsersRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final TownsRepository townsRepository;
    private final RolesRepository rolesRepository;

    private Map<String, User> listUsers = new HashMap<>();

    public Optional<User> findUserById(Long id) {
        return usersRepository.findById(id);
    }

    public User getUserByNickname(String nickname) {
        if (listUsers.containsKey(nickname)) {
            return listUsers.get(nickname);
        }
        User user = usersRepository.findUserByNickname(nickname);
        listUsers.put(nickname, user);
        return user;
    }

    // проверка на существующего пользователя в БД
    public boolean checkExistUser(String email, String login) {
        return usersRepository.findUserByEmail(email) != null || usersRepository.findUserByNickname(login) != null;
    }

    public String saveOrUpdateUser(User user) {
//        if (usersRepository.findUserByEmail(user.getEmail()) != null || usersRepository.findUserByNickname(user.getNickname()) != null) {
//            return "Указанная почта или ник уже существует";
//        }
        usersRepository.save(user);
        listUsers.put(user.getNickname(), user);
        return "Пользователь " + user.getNickname() + " создан";
    }

    public Town getTown(String town) {
        return townsRepository.findTownByTown(town);
    }

    public Town saveTown(Town town) {
        return townsRepository.save(town);
    }

    public Role getRole(String role) {
        return rolesRepository.findRoleByRole(role);
    }
}
