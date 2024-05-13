package ru.ostap.notificationservice.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RegistrationMessage {
    private String username;
    private String email;

    @JsonCreator
    public RegistrationMessage(@JsonProperty("username") String username, @JsonProperty("email") String email) {
        this.username = username;
        this.email = email;
    }
}
