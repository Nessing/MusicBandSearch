package ru.project.musicbandsearch.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "about")
    private String about;

    // ОДИН пользователь может владеть НЕСКОЛЬКИМИ инструментами
    @ManyToMany
    @JoinTable(name = "user_instruments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id")
    )
    private List<Instrument> instrument;

    // ОДИН пользователь может заинтересован в НЕСКОЛЬКИХ жанрах
    @ManyToMany
    @JoinTable(name = "user_genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genre;

    // ОДИН пользователь может проживать только в ОДНОМ городе
    @ManyToOne
    @JoinTable(name = "user_town",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "town_id")
    )
    private Town town;

    // ОДИН пользователь одновременно может иметь только ОДНУ роль
    @ManyToOne
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Role role;
}