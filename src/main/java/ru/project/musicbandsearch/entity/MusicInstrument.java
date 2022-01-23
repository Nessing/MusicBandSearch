package ru.project.musicbandsearch.entity;

import javax.persistence.*;

@Entity
@Table(name = "music_instrument_table")
public class MusicInstrument {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
}
