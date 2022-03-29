package ru.project.musicbandsearch.configurations.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.repositories.UsersRepository;

import java.util.Optional;

@Service
public class DBUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(username);
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found"));
        return user.map(UserDetailsImpl::new).get();
    }
}
