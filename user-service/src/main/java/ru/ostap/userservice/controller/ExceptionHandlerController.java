package ru.ostap.userservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ostap.userservice.util.exception.NotValidUserException;
import ru.ostap.userservice.util.exception.UserNotFoundException;
import ru.ostap.userservice.util.response.UserErrorResponse;

import java.sql.Timestamp;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundException ex) {
        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found",
                new Timestamp(System.currentTimeMillis())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(NotValidUserException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMsg(), ex.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
