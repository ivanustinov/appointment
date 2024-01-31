package ru.ustinov.appointment.exception;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * //TODO add comments.
 *
 * @author Ivan Ustinov(ivanustinov1985@yandex.ru)
 * @version 1.0
 * @since 30.01.2024
 */
@Getter
public class AppException extends ResponseStatusException {

    private final ErrorAttributeOptions options;

    private String[] params;

    public AppException(HttpStatus status, ErrorAttributeOptions options, String message, String... params) {
        super(status, message);
        this.options = options;
        this.params = params;
    }

    public AppException(HttpStatus status, ErrorAttributeOptions options, String message) {
        super(status, message);
        this.options = options;
    }

}

