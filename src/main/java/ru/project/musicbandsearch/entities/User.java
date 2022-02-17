package ru.project.musicbandsearch.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    /*
    @Column(name = "user_password")
    private String userPassword;
    */

    @ManyToMany
    @JoinTable(
            name = "user_info_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_info_id")
    )
    private List<UserInfo> userInfo;

    @ManyToMany
    @JoinTable(
            name = "role_id",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> userRoles;

}
