package ru.ostap.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.security.JWTUtil;
import ru.ostap.userservice.service.UserService;
import ru.ostap.userservice.util.DtoConverter;
import ru.ostap.userservice.util.ValidationUtils;
import ru.ostap.userservice.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/administration")
@RequiredArgsConstructor
public class UserController extends ExceptionHandlerController {
    private final UserService userService;
    private final ValidationUtils validationUtils;
    private final DtoConverter converter;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;


    @GetMapping("/getOne/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return ResponseEntity.ok(modelMapper.map(user, UserDTO.class));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getUsers() {
        if (userService.findAll().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userService.findAll());
    }

    @PutMapping("/create")
    public ResponseEntity<Map<String, String>> addNewUser(@RequestBody UserDTO userDTO) {
        validationUtils.validationRequest(userDTO);
        userService.save(converter.convertToUser(userDTO));

        return ResponseEntity.ok().body(Map.of("jwt-token", jwtUtil.generateToken(userDTO.getUsername())));
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

