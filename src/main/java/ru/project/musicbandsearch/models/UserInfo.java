package ru.project.musicbandsearch.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserInfo {
    private UUID userId;

    private String userEmail;

    private List<String> role;
}
