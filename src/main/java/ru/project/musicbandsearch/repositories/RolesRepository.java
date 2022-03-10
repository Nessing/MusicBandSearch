package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(String role);
}