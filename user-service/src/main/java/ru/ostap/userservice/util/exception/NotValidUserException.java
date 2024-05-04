package ru.ostap.userservice.util.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class NotValidUserException extends RuntimeException {
    String msg;
    Timestamp timestamp;
}
