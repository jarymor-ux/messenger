package ru.ostap.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ostap.userservice.dto.AuthenticationDTO;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.messages.RegistrationMessage;
import ru.ostap.userservice.security.JWTUtil;
import ru.ostap.userservice.service.AuthenticationService;
import ru.ostap.userservice.service.UserService;
import ru.ostap.userservice.util.response.CreateUserResponse;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthenticationController extends ExceptionHandlerController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationService authenticationService;
    private KafkaTemplate<String, RegistrationMessage> kafkaTemplate;

    public void sendNotification(RegistrationMessage message) {
        kafkaTemplate.send("notificationTopic", message);
    }

    @PostMapping("/registration")
    public ResponseEntity<CreateUserResponse> registration(@RequestBody @Valid UserDTO userDTO) {
        userService.register(userDTO);
        sendNotification(new RegistrationMessage(userDTO.getUsername(), userDTO.getEmail()));
        return ResponseEntity.ok().body(new CreateUserResponse(
                "User with name " + userDTO.getUsername() + " successfully registered",
                jwtUtil.generateToken(userDTO.getUsername())));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        authenticationService.authenticate(authenticationDTO);
        return ResponseEntity.ok().body(authenticationService.authenticate(authenticationDTO));
    }


}

