package ru.project.musicbandsearch.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    @Column(name = "genre_title")
    private String genreTitle;
}