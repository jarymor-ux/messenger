package ru.ostap.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ostap.userservice.dto.AuthenticationDTO;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController extends ExceptionHandlerController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> registration(@RequestBody @Valid UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        return userService.authenticate(authenticationDTO);
    }

}

