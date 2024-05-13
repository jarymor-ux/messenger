package ru.ostap.userservice.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrationMessage {
    private String username;
    private String email;
}
