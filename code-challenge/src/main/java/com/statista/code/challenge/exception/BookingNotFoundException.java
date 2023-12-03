package com.statista.code.challenge.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * BookingNotFoundException is a custom exception thrown when a booking cannot be found.
 * It is annotated with @ResponseStatus to return a 404 Not Found HTTP status code when this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND) // This annotation ensures that Spring handles this exception with a 404 Not Found status code.
@NoArgsConstructor
public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException(String message) {
        super(message);
    }

    // If you plan to log the stack trace or need it for debugging, you can also add a constructor that accepts a cause.
    public BookingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
