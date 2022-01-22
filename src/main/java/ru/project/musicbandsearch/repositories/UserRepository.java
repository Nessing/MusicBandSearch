package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.musicbandsearch.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User,String>{
    Optional<User> findByEmail(String email);
}
