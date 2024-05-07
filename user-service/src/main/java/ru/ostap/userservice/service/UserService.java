package ru.ostap.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.dto.AuthenticationDTO;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.mapper.UserMapper;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.repository.UserRepository;
import ru.ostap.userservice.security.JWTUtil;
import ru.ostap.userservice.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    public void save(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void update(User user) {
        if (userRepository.findById(user.getId()).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<UserDTO> findAll() {
        return UserMapper.INSTANCE.toUserDTOList(userRepository.findAll());
    }

    public void deleteUser(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

    public ResponseEntity<Map<String, String>> register(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toUser(userDTO);
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok().body(Map.of("jwt-token", jwtUtil.generateToken(user.getUsername())));
    }



    public ResponseEntity<Map<String, String>> authenticate(AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "Incorrect credentials!"));
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return ResponseEntity.ok().body(Map.of("jwt-token", token));
    }
}
