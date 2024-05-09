package ru.ostap.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.mapper.UserMapper;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.repository.UserRepository;
import ru.ostap.userservice.util.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void save(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.save(user);
    }

    public UserDTO findById(Long id) {
        return userMapper.toUserDTO(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public List<UserDTO> findAll() {
        return userMapper.toUserDTOs(userRepository.findAll());
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public void register(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info(user.toString());
        userRepository.save(user);
    }
}
