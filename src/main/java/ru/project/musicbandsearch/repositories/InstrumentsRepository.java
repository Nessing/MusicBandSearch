package ru.project.musicbandsearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.project.musicbandsearch.entities.Instrument;

@Repository
public interface InstrumentsRepository extends JpaRepository<Instrument, Long> {
    Instrument findInstrumentByInstrument(String instrument);
}
