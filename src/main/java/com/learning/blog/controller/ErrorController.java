package com.learning.blog.controller;


import com.learning.blog.domain.dtos.APIErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<APIErrorResponse> handleException(Exception e) {
        log.error("caught exception: "  + e);
        APIErrorResponse apiErrorResponse = APIErrorResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred").build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<APIErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        APIErrorResponse error = APIErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value()).message(e.getMessage()).build();
        return new  ResponseEntity<>(error, HttpStatus.CONFLICT);
    }


    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<APIErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        APIErrorResponse error = APIErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value()).message("Incorrect User mail or password").build();
        return new  ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<APIErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        APIErrorResponse error = APIErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value()).message(e.getMessage()).build();
        return new  ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
