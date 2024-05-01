package ru.ostap.userservice.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(min = 2,max = 15, message = "The username must be between 2 and 15 characters long")
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 64)
    @NotNull
    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid")
    private String email;


    @NotNull
    @Column(name = "password_hash", nullable = false)
    @Size(min = 8, max = 64, message = "The password must be between 8 and 64 characters long")
    @Pattern(
            regexp = "^(?=.*[A-ZА-Я])(?=.*[a-zа-я])(?=.*\\d)(?=.*[!@#$%^&*()-_+=])[A-Za-zА-Яа-я0-9!@#$%^&*()-_+=]{8,64}$",
            message = "Password should be valid"
    )
    private String password;

    @Column(name = "registration_date")
    private Timestamp registrationDate = new Timestamp(System.currentTimeMillis());


    @NotNull
    @Column(name = "online_status", nullable = false)
    private Boolean onlineStatus = false;

    @NotNull
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = false;

}