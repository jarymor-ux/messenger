package ru.ostap.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@Valid
@ToString
public class UserDTO {
    @Size(min = 2, max = 15, message = "The username must be between 2 and 15 characters long")
    @NotNull(message = "Username should be not empty")
    private String username;

    @Size(max = 64)
    @NotNull(message = "Email should be not empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password should be not empty")
    @Size(min = 8, max = 64, message = "The password must be between 8 and 64 characters long")
    @Pattern(
            regexp = "^(?=.*[A-ZА-Я])(?=.*[a-zа-я])(?=.*\\d)(?=.*[!@#$%^&*()-_+=])[A-Za-zА-Яа-я0-9!@#$%^&*()-_+=]{8,64}$",
            message = "Password should be valid"
    )
    @JsonIgnore
    private String password;

}
