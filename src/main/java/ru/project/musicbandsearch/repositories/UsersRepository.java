package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserByNickname(String nickname);
}
