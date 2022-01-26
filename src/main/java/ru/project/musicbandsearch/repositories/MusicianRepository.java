package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.musicbandsearch.entity.Musician;

import java.util.UUID;

public interface MusicianRepository extends JpaRepository<Musician, UUID> {
}
