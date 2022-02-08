package ru.project.musicbandsearch.entities.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.musicbandsearch.entities.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class Musician implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

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
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )   // роль пользователя в системе
    private List<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "USER_INSTRUMENT",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INSTRUMENT_ID")
    )   // Музыкальный инструмент (тут для указания музыкантом используемым муз. инструментом)
    private List<Instrument> instruments;

    @ManyToMany
    @JoinTable(
            name = "USER_GENRE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )   // жанр музыки (тут для указания музыкантом жанра в котором он играет)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "USER_TOWN",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "TOWN_ID")
    )  //город (тут для указания музыкантом города проживания)
    private List<Town> towns;

}