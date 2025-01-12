package com.example.usermanagementservice.advices;

import com.example.usermanagementservice.dtos.InvalidTokenExceptionDto;
import com.example.usermanagementservice.exceptions.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<InvalidTokenExceptionDto> handleInvalidTokenException() {
        InvalidTokenExceptionDto invalidTokenExceptionDto = new InvalidTokenExceptionDto();
        invalidTokenExceptionDto.setMessage("Invalid Token Passed");
        invalidTokenExceptionDto.setDetail("Try to Login Again and Retry");
        return new ResponseEntity<>(invalidTokenExceptionDto, HttpStatus.UNAUTHORIZED);
    }
}
