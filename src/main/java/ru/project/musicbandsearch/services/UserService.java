package ru.project.musicbandsearch.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.config.security.interfaces.ITokenService;
import ru.project.musicbandsearch.dto.AuthRequestDto;
import ru.project.musicbandsearch.dto.AuthResponseDto;
import ru.project.musicbandsearch.entity.User;
import ru.project.musicbandsearch.exceptionHandler.models.Forbidden;
import ru.project.musicbandsearch.models.UserInfo;
import ru.project.musicbandsearch.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ITokenService iTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponseDto login(AuthRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user != null)
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
                throw new Forbidden("Bad credentials");
        List<String> roles = new ArrayList<>();
        assert user != null;
        user.getRoles().forEach(role -> roles.add(role.getTitle()));
        UserInfo userInfo = UserInfo.builder()
                .userId(user.getId())
                .userEmail(user.getEmail())
                .role(roles)
                .build();
        String token = iTokenService.generateToken(userInfo);
        return new AuthResponseDto(user.getId(), token);
    }
}
