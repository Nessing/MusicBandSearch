package ru.project.musicbandsearch.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.repositories.UserRepository;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

