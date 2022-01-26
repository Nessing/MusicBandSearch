package ru.project.musicbandsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MUSICIAN")
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "NICKNAME")
    private String nickname; // прозвище музыканта

    @Column(name = "INFO")
    private String info;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_ROLE",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE")
    )   // роль пользователя в системе
    private List<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_INSTRUMENT",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_INSTRUMENT")
    )   // Музыкальный инструмент (тут для указания музыкантом используемым муз. инструментом)
    private List<Instrument> instruments;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_GENRE",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_GENRE")
    )   // жанр музыки (тут для указания музыкантом жанра в котором он играет)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_TOWN",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_TOWN")
    )  //город (тут для указания музыкантом города проживания)
    private List<Town> towns;
}