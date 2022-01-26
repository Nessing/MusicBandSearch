package ru.project.musicbandsearch.config.security.interfaces;

import ru.project.musicbandsearch.models.UserInfo;

public interface ITokenService {
    String generateToken(UserInfo user);

    UserInfo parseToken(String token);
}
