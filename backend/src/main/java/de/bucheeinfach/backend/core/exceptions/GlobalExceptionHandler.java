package de.bucheeinfach.backend.core.exceptions;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .apiPath(request.getDescription(false))
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRecordException(DuplicateRecordException ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .apiPath(request.getDescription(false))
                .status(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse>handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request){
        StringBuilder errorMessages = new StringBuilder();
        errorMessages.append("Error: ");
        ex.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" - "));
        ErrorResponse errorResponse = ErrorResponse.builder()
                .apiPath(request.getDescription(false))
                .status(HttpStatus.BAD_REQUEST)
                .message(errorMessages.charAt(errorMessages.length()-2)=='-' ? errorMessages.substring(0,errorMessages.length()-3) : errorMessages.toString())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .apiPath(request.getDescription(false))
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}