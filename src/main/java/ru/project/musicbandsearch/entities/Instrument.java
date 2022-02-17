package ru.project.musicbandsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "instruments")
public class Instrument {
    @Id
    @Column(name = "instrument_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer instrumentId;

    @Column(name = "instrument_title")
    private String instrumentTitle;
}