package ru.project.musicbandsearch.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "musician_group_table")
@SecondaryTable(name = "user_table",pkJoinColumns = @PrimaryKeyJoinColumn(name="id", referencedColumnName="id_user"))
public class Musician {
    @Id
    @Getter
    @Column(name = "id_user")
    private UUID id;
    @Getter
    @Column(table = "user_table", name = "email")
    private String email;
    @Getter
    @Column(table = "user_table", name = "location")
    private String location;
    @ManyToMany(mappedBy = "musicians")
    private List<Group> groups;
}
