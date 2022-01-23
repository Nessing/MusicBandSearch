package ru.project.musicbandsearch.config.security.service;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.config.security.interfaces.ITokenService;
import ru.project.musicbandsearch.models.UserInfo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class JWTTokenService implements ITokenService {
    @Value("$(jwt.secret)")
    private String JWT_SECRET;

    @Override
    public String generateToken(UserInfo user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);
        String compactTokenString = Jwts.builder()
                .claim("id", user.getUserId())
                .claim("sub", user.getUserEmail())
                .claim("roles", user.getRole())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return "Bearer " + compactTokenString;
    }

    @Override
    public UserInfo parseToken(String token) throws ExpiredJwtException {
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token);
        String email = jwsClaims.getBody()
                .getSubject();
        String userId = jwsClaims.getBody()
                .get("id", String.class);
        List<String> roles = jwsClaims.getBody()
                .get("roles", List.class);
        return UserInfo.builder()
                .userId(UUID.fromString(userId))
                .userEmail(email)
                .role(roles)
                .build();
    }
}
