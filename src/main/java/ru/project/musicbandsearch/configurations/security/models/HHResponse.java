package ru.project.musicbandsearch.configurations.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HHResponse implements Serializable {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
}
