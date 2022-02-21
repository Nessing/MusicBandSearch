package ru.project.musicbandsearch.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
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
    private String last_name;

    @Column(name = "telephone")
    private String telephone;

    // ОДИН пользователь может владеть НЕСКОЛЬКИМИ инструментами
    @OneToMany
    @JoinTable(name = "user_instruments",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "instrument_id")
    )
    private List<Instrument> instrument;

    // ОДИН пользователь может заинтересован в НЕСКОЛЬКИХ жанрах
    @OneToMany
    @JoinTable(name = "user_genres",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genre;

    // ОДИН пользователь может проживать только в ОДНОМ городе
    @OneToOne
    @PrimaryKeyJoinColumn
    private Town town;

    // ОДИН пользователь одновременно может иметь только ОДНУ роль
    @OneToOne
    @PrimaryKeyJoinColumn
    private Role role;
}
