package ru.project.musicbandsearch.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "music_style_table")
public class Style {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @ManyToMany(mappedBy = "styles")
    private List<Group> groups;
}
