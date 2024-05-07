package ru.ostap.userservice.util.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class UserErrorResponse {
    private String msg;
    private Timestamp timestamp;


}
