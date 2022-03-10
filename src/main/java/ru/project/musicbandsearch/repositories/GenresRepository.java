package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.Genre;

import java.util.List;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {
    List<Genre> getAllById(Long[] id);
}