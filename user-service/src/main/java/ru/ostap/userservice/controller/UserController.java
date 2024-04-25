package ru.ostap.userservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/newUser")
    public ResponseEntity newUser(@RequestBody User user) {
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }
        userService.save(user);
        return ResponseEntity.ok().body("User successfully registered: " +
                userService.findByEmail(user.getEmail()).getUsername());
    }

}
