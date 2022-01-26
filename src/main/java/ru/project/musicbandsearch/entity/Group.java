package ru.project.musicbandsearch.entity;

import lombok.Getter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "group_table")
public class Group {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "title")
    private String title;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "group_style_table",
            joinColumns = @JoinColumn(name = "id_group"),
            inverseJoinColumns = @JoinColumn(name = "id_style"))
    private List<Style> styles;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "musician_group_table",
            joinColumns = @JoinColumn(name = "id_group"),
            inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<Musician> musicians;
    @Column(name = "price")
    private Double price;
    @Column(name = "program_duration")
    private Double programDuration;
}
