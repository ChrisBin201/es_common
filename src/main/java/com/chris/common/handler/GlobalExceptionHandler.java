package com.chris.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(fe -> "Invalid "+ fe.getField() + ": "+ fe.getDefaultMessage()).collect(Collectors.toList());
        log.error("RuntimeException: ", ex);
        return new ResponseEntity<>(getErrorsMap(errors,HttpStatus.BAD_REQUEST.name()), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> getErrorsMap(List<String> errors, String errorCode) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", errorCode);
        errorResponse.put("message", errors.size()>1 ? errors: errors.get(0));
        errorResponse.put("timestamp", LocalDateTime.now());
        return errorResponse;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundException(UserNotFoundException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        log.error("RuntimeException: ", ex);
        return new ResponseEntity<>(getErrorsMap(errors,HttpStatus.NOT_FOUND.name()), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, Object>> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        log.error("RuntimeException: ", ex);
        return new ResponseEntity<>(getErrorsMap(errors,HttpStatus.INTERNAL_SERVER_ERROR.name()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, Object>> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        log.error("RuntimeException: ", ex);
        return new ResponseEntity<>(getErrorsMap(errors,HttpStatus.INTERNAL_SERVER_ERROR.name()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public final ResponseEntity<Map<String, Object>> handleCommonExceptions(CommonException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        int httpStatusCode = ex.getHttpStatusCode();
        String errorCode = ex.getErrorCode();
        log.error("CommonException: ", ex);
        return new ResponseEntity<>(getErrorsMap(errors, errorCode), new HttpHeaders(), HttpStatus.valueOf(httpStatusCode));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
        public UserNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
