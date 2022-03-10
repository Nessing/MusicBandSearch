package ru.project.musicbandsearch.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "town")
    private String town;
}