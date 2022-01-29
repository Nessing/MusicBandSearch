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
@Table(name = "CUSTOMER")
public class Customer implements User {
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

    @Column(name = "ORGANIZATION")
    private String organization; // может быть null

    @Column(name = "INFO")
    private String info;

    @ManyToMany
    @JoinTable(
            name = "GROUP_ROLE",
            joinColumns = @JoinColumn(name = "ID_GROUP"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE")
    )   // роль пользователя в системе
    private List<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_INSTRUMENT",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_INSTRUMENT")
    )   // музыкальный инструмент (тут для поиска группы по инструменту)
    private List<Instrument> instruments;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_GENRE",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_GENRE")
    )   // жанр музыки (тут для поиска группы по жанру)
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "MUSICIAN_TOWN",
            joinColumns = @JoinColumn(name = "ID_MUSICIAN"),
            inverseJoinColumns = @JoinColumn(name = "ID_TOWN")
    )   // город (тут для поиска по городу)
    private List<Town> towns;
}