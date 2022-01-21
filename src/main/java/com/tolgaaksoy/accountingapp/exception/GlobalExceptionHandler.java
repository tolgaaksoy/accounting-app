package com.tolgaaksoy.accountingapp.exception;

import com.tolgaaksoy.accountingapp.response.APIResponse;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults().excluding(ErrorAttributeOptions.Include.EXCEPTION));
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<APIResponse> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(APIResponse.builder()
                .status(ex.getHttpStatus().value())
                .message(ex.getMessage())
                .time(Instant.now())
                .build(), ex.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse> handleAccessDeniedException() {
        return new ResponseEntity<>(APIResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .message("Access denied")
                .time(Instant.now())
                .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleException() {
        return new ResponseEntity<>(APIResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Something went wrong")
                .time(Instant.now())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
