package vn.base.app.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum EErorr {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Client error"),
    BAD_REQUEST_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "Incorrect passed data format"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "You don't have access rights to the content"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    CONFLICT(HttpStatus.CONFLICT, "Resource already exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong");

    final HttpStatus status;
    final String message;

    EErorr(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
