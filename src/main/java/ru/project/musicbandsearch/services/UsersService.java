package ru.project.musicbandsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.repositories.UsersRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository repositories;

    public Optional<User> findMusicianById(Long id) {
        return repositories.findById(id);
    }
}
