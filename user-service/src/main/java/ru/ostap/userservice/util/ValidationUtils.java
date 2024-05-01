package ru.ostap.userservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.util.exception.NotValidUserException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.sql.Timestamp;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ValidationUtils {

    private final Validator validator;

    public <T> void validationRequest(T req){
        if (req != null) {
            Set<ConstraintViolation<T>> result = validator.validate(req);
            if (!result.isEmpty()) {
                String resultsValidations = result.stream()
                        .map(ConstraintViolation::getMessage)
                        .reduce((s1,s2) -> s1 + ". " + s2).orElse("");
                throw new NotValidUserException(resultsValidations, new Timestamp(System.currentTimeMillis()));
            }
        }
    }

}
