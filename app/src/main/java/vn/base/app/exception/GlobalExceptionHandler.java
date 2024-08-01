package vn.base.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import vn.base.app.utils.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> ExceptionHandler() {
        CustomResponse<String> response = new CustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong");
        return response.response();
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> AccessDeniedExceptionHandler() {
        CustomResponse<String> response = new CustomResponse<>(HttpStatus.FORBIDDEN, "Access Denied");
        return response.response();
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<Object> CustomExceptionHandler(CustomException exception) {
        CustomResponse<String> response = new CustomResponse<>(exception.getStatus(), exception.getMessage());
        return response.response();
    }

}
