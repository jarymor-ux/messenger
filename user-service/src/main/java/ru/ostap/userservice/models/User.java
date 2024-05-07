package ru.ostap.userservice.models;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Component
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Size(min = 2, max = 15, message = "The username must be between 2 and 15 characters long")
    @NotNull(message = "Username should be not empty")
    @Column(name = "username", nullable = false)
    private String username;

    @Size(max = 64)
    @NotNull(message = "Email should be not empty")
    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password should be not empty")
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


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new LinkedHashSet<>();

}