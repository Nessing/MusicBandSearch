package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.musicbandsearch.entities.User;


public interface UserRepository extends JpaRepository <User, Long> {

}
