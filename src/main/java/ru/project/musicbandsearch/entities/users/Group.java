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
public class Group implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")  //название группы
    private String group_name;

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
    )   // музыкальный инструмент (тут для поиска музыканта по инструменту)
    private List<Instrument> instruments;

    @ManyToMany
    @JoinTable(
            name = "USER_GENRE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )   // жанр музыки (тут для поиска музыканта по жанру)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "USER_TOWN",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "TOWN_ID")
    )   // город (тут для поиска по городу)
    private List<Town> towns;
}