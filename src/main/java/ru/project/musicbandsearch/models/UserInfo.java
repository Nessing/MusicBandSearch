package ru.project.musicbandsearch.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfo {
    private String userId;

    private String userEmail;

    private List<String> role;
}
