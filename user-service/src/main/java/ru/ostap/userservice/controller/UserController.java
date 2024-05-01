package ru.ostap.userservice.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.service.UserService;
import ru.ostap.userservice.util.ValidationUtils;

import ru.ostap.userservice.util.exception.UserNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends ExceptionHandlerController{
    private final UserService userService;
    private final ValidationUtils validationUtils;

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        if (userService.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userService.findAll());

    }

    @PutMapping("/create")
    public ResponseEntity<String> addNewUser(@RequestBody User user) {
        validationUtils.validationRequest(user);
        userService.save(user);
        return ResponseEntity.ok().body("User successfully registered");
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        if (userService.findById(user.getId()) == null) {
            return ResponseEntity.noContent().build();
        }
        validationUtils.validationRequest(user);
        userService.save(user);

        return ResponseEntity.ok().body("User successfully updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        if (userService.findById(id) == null) {
            throw new UserNotFoundException();
        }
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User successfully deleted");
    }
}
