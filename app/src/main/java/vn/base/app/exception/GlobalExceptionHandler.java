package vn.base.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import vn.base.app.utils.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> ExceptionHandler() {
        CustomResponse<String> response = new CustomResponse<>(EErorr.INTERNAL_SERVER_ERROR.getStatus(),
                EErorr.INTERNAL_SERVER_ERROR.getMessage());
        return response.response();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> MethodArgumentTypeMismatchExceptionHandler() {
        CustomResponse<String> response = new CustomResponse<>(EErorr.BAD_REQUEST.getStatus(),
                EErorr.BAD_REQUEST.getMessage());
        return response.response();
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> AccessDeniedExceptionHandler() {
        CustomResponse<String> response = new CustomResponse<>(EErorr.FORBIDDEN.getStatus(),
                EErorr.FORBIDDEN.getMessage());
        return response.response();
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<Object> CustomExceptionHandler(CustomException exception) {
        CustomResponse<String> response = new CustomResponse<>(exception.getStatus(), exception.getMessage());
        return response.response();
    }

}
