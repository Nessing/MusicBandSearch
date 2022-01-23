package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.musicbandsearch.entity.Group;

import java.util.UUID;

public interface GroupRepository extends JpaRepository<Group, UUID> {
}
