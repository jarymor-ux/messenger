package ru.ostap.userservice.util.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;


@Getter
@Setter
public class UserErrorResponse {
    private String message;
    private LocalDateTime timestamp;

    public UserErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now(ZoneId.of("UTC+3"));
    }
}
