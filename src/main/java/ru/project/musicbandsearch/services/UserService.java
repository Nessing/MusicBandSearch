package ru.project.musicbandsearch.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.project.musicbandsearch.entities.User;
import ru.project.musicbandsearch.entities.UserDto;
import ru.project.musicbandsearch.repositories.UserRepository;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    public Optional<UserDto> findUserDtoById(Long id) {
        return userRepository.findById(id).map(this::toDto);
    }

    public List<UserDto> findUserDtosByIds(List<Long> ids) {
        return userRepository.findByIdIn(ids).stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public Page<UserDto> findAll(Specification<User> spec, int page, int pageSize) {
        return userRepository.findAll(spec, PageRequest.of(page - 1, pageSize)).map(this::toDto);
    }

    public UserDto saveOrUpdate(UserDto userDto) throws ParseException {
        return toDto(userRepository.save(toEntity(userDto)));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User toEntity(UserDto productDto) throws ParseException {
        return modelMapper.map(productDto, User.class);
    }
}