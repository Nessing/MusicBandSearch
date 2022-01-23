package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.musicbandsearch.entity.Style;

public interface StyleRepository extends JpaRepository<Style,Integer> {
}
