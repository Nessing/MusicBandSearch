package ru.project.musicbandsearch.repositorises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicBandRepository extends JpaRepository<MusicBand, Long>, JpaSpecificationExecutor<MusicBand> {

}
