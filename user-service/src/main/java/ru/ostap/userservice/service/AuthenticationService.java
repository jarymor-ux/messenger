package ru.ostap.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.dto.AuthenticationDTO;
import ru.ostap.userservice.messages.RegistrationMessage;
import ru.ostap.userservice.security.JWTUtil;
import ru.ostap.userservice.util.request.CreateUserRequest;
import ru.ostap.userservice.util.response.CreateUserResponse;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final NotificationService notificationService;

    public Map<String, String> authenticate(AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }

    public CreateUserResponse registration(CreateUserRequest user) {
        userService.register(user);
        notificationService.sendNotification(new RegistrationMessage(user.getUsername(), user.getEmail()));
        return new CreateUserResponse(
                "User with name " + user.getUsername() + " successfully registered",
                jwtUtil.generateToken(user.getUsername()));
    }
}
