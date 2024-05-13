package ru.ostap.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ostap.userservice.dto.UserDTO;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.security.JWTUtil;
import ru.ostap.userservice.service.UserService;
import ru.ostap.userservice.util.response.CreateUserResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/administration")
public class UserController extends ExceptionHandlerController {
    private final UserService userService;
    private final JWTUtil jwtUtil;


    @PostMapping()
    public ResponseEntity<CreateUserResponse> addNewUser(@RequestBody @Valid UserDTO userDTO) {
        userService.save(userDTO);
        return ResponseEntity.ok().body(new CreateUserResponse(
                "User " + userDTO.getUsername() + " successfully created",
                jwtUtil.generateToken(userDTO.getUsername())));
    }

    @PatchMapping()
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok().body("User " + user.getUsername() + "successfully updated");
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        String username = userService.findById(id).getUsername();
        userService.deleteUser(id);
        return ResponseEntity.ok().body("User " + username + " successfully deleted");
    }
}
