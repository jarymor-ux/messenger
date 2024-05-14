package ru.ostap.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ostap.userservice.dto.AuthenticationDTO;
import ru.ostap.userservice.service.AuthenticationService;
import ru.ostap.userservice.util.request.CreateUserRequest;
import ru.ostap.userservice.util.response.CreateUserResponse;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthenticationController extends ExceptionHandlerController {
    private final AuthenticationService authenticationService;


    @PostMapping("/registration")
    public ResponseEntity<CreateUserResponse> registration(@RequestBody @Valid CreateUserRequest user) {
        return ResponseEntity.ok().body(authenticationService.registration(user));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        return ResponseEntity.ok().body(authenticationService.authenticate(authenticationDTO));
    }


}

