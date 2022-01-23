package ru.project.musicbandsearch.repositorises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicianRepository extends JpaRepository<Musician, Long>, JpaSpecificationExecutor<Musician> {

}
