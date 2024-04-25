package ru.ostap.userservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @Size(min = 2,max = 15)
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
    @Size(min = 8, max = 64)
    private String password;

    @Column(name = "registration_date")
    private Timestamp registrationDate = new Timestamp(System.currentTimeMillis());

}