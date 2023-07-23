package com.example.auth.exeception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
// Controller advice to handle exceptions globally
@RestControllerAdvice
public class ExceptionHandler {

    // Handle validation errors
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(MethodArgumentNotValidException e){
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }

    // Handle invalid credentials
    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadCredentials(BadCredentialsException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid Login Credentials");
        return error;
    }

    // Handle invalid JSON
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidJson(HttpMessageNotReadableException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", "Invalid JSON Data Sent to Server");
        return error;
    }

    // Handle expired JWT
    @org.springframework.web.bind.annotation.ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleExpiredToken(ExpiredJwtException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Your Session is Timed Out. Please Login Again.");
        return error;
    }

    // Handle unsupported HTTP method
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Map<String, String> handleInvalidMethod(HttpRequestMethodNotSupportedException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Method Not Allowed");
        return error;
    }

    // Handle not found
    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(NoSuchElementException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "Resource Not Found");
        return error;
    }

    // Handle duplicate entries
    @org.springframework.web.bind.annotation.ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicate(SQLIntegrityConstraintViolationException e){
        Map<String, String> error = new HashMap<>();
        error.put("message", "User Already Exist");
        return error;
    }

    // Handle user not found
    @org.springframework.web.bind.annotation.ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Map<String, String> handleUserNotFound(UsernameNotFoundException e) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "User Not Found");
        return error;
    }

}