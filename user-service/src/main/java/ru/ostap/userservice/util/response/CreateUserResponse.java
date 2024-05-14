package ru.ostap.userservice.util.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Data
public class CreateUserResponse {
    private String message;
    private String jwtToken;
    private LocalDateTime timestamp;

    public CreateUserResponse(String message, String jwtToken) {
        this.jwtToken = jwtToken;
        this.message = message;
        this.timestamp = LocalDateTime.now(ZoneId.of("UTC+3"));
    }


}
