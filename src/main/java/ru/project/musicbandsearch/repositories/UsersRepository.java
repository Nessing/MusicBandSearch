package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    User findUserByNickname(String nickname);
    List<User> findUsersByRole_Role(String role);
    List<User> findUsersByGenre_Genre(String genre);
    List<User> findUsersByRole_RoleAndGenre_Genre(String role, String genre);
}
