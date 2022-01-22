package ru.project.musicbandsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequestDto {
    private String email;
    private String password;
}
