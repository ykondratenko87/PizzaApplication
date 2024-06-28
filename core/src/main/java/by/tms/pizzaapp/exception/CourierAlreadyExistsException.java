package by.tms.pizzaapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CourierAlreadyExistsException extends RuntimeException {
    public CourierAlreadyExistsException(String message) {
        super(message);
    }
}