package ru.ostap.userservice.util.response;

import lombok.Data;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Data
public class CreateUserResponse {
    private String message;
    private String jwtToken;
    private ZonedDateTime timestamp;

    public CreateUserResponse(String message, String jwtToken) {
        this.jwtToken = jwtToken;
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
    }


}
