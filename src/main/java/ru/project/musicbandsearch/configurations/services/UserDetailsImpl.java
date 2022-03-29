package ru.project.musicbandsearch.configurations.services;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.project.musicbandsearch.entities.User;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.isActive = true;
        this.authorities = Arrays.stream(user.getRole().getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public UserDetailsImpl() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {

        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
