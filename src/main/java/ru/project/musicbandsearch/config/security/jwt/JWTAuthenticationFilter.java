package ru.project.musicbandsearch.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.project.musicbandsearch.config.security.interfaces.ITokenService;
import ru.project.musicbandsearch.exceptionHandler.models.Forbidden;
import ru.project.musicbandsearch.models.UserInfo;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final ITokenService tokenService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        if (authorizationHeaderIsInvalid(authorizationHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = createToken(authorizationHeader);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (ExpiredJwtException exp) {
            exp.printStackTrace();
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) throws ExpiredJwtException {
        String token = authorizationHeader.replace("Bearer ", "");
        UserInfo userInfo = tokenService.parseToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (userInfo.getRole() != null && !userInfo.getRole().isEmpty()) {
            userInfo.getRole().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
        return new UsernamePasswordAuthenticationToken(userInfo, null, authorities);
    }
}
