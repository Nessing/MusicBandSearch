package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.Town;

@Repository
public interface TownsRepository extends JpaRepository<Town, Long> {
    Town findTownByTown(String town);
}