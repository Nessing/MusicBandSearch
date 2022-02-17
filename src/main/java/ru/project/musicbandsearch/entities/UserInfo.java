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
@Table(name = "users_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "info_id")
    private Long infoId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @ManyToMany
    @JoinTable(
            name = "instruments",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "instrument_id")
    )
    private List<Instrument> instruments;

    @ManyToMany
    @JoinTable(
            name = "genres",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @ManyToMany
    @JoinTable(
            name = "towns",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "town_id")
    )
    private List<Town> towns;
}

