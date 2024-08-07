package vn.base.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.ws.rs.ForbiddenException;
import vn.base.app.utils.CustomResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> ExceptionHandler(Exception exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(EErorr.INTERNAL_SERVER_ERROR.getStatus(),
                EErorr.INTERNAL_SERVER_ERROR.getMessage());
        return response.response();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(EErorr.BAD_REQUEST.getStatus(),
                exception.getFieldError().getDefaultMessage());
        return response.response();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> MethodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(EErorr.BAD_REQUEST_TYPE_MISMATCH.getStatus(),
                EErorr.BAD_REQUEST_TYPE_MISMATCH.getMessage());
        return response.response();
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity<Object> ForbiddenExceptionHandler(ForbiddenException exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(EErorr.FORBIDDEN.getStatus(), exception.getMessage());
        return response.response();
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> AccessDeniedExceptionHandler(AccessDeniedException exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(EErorr.FORBIDDEN.getStatus(),
                EErorr.FORBIDDEN.getMessage());
        return response.response();
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<Object> CustomExceptionHandler(CustomException exception) {
        exception.printStackTrace();
        CustomResponse<String> response = new CustomResponse<>(exception.getStatus(), exception.getMessage());
        return response.response();
    }

}
