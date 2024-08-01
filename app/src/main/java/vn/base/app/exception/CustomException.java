package vn.base.app.exception;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomException extends RuntimeException {
    HttpStatus status;
    String message;
    EErorr erorr;

    public CustomException(EErorr erorr) {
        this.erorr = erorr;
        this.status = this.erorr.getStatus();
        this.message = this.erorr.getMessage();
    }

    public CustomException(HttpStatus status, String message) {
        this.erorr = null;
        this.status = status;
        this.message = message;
    }
}
