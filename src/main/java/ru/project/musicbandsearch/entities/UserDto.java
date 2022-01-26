package ru.project.musicbandsearch.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

    public class UserDto {
        private Long id;
        private String name;
        private String info;

        public UserDto (User user) {
            this.id = user.getUserId();
            this.name = user.getUserName();
            this.info = user.getUserInfo();
        }
    }