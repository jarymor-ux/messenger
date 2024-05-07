package ru.ostap.userservice.util.response;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;


@Getter
@Setter
public class UserErrorResponse {
    private String message;
    private ZonedDateTime timestamp;

    public UserErrorResponse(String message) {
        this.message = message;
        this.timestamp = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
    }
}
