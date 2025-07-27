package com.AppSenelec.exception;

import com.AppSenelec.dto.ErrorDto;
import com.AppSenelec.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> handleAlreadyExists(AlreadyExistsException ex) {
        ApiResponse<ErrorDto> response = ApiResponse.<ErrorDto>builder()
                .message("Erreur : " + ex.getMessage())
                .date(LocalDateTime.now())
                .status(ex.getStatus().value())
                .data(ex.getErrorDto())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorDto>> handleNotFound(NotFoundException ex) {
        ApiResponse<ErrorDto> response = ApiResponse.<ErrorDto>builder()
                .message("Erreur : " + ex.getMessage())
                .date(LocalDateTime.now())
                .status(ex.getStatus().value())
                .data(ex.getErrorDto())
                .build();
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> Map.of("field", err.getField(), "message", err.getDefaultMessage()))
                .collect(Collectors.toList());
        
        Map<String, Object> errorData = Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "status", HttpStatus.BAD_REQUEST.name(),
                "message", "Erreur de validation",
                "errors", errors
        );
        
        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .message("Erreur de validation")
                .date(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .data(errorData)
                .build();
        
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Map<String, Object>>> handleInvalidJson(HttpMessageNotReadableException ex) {
        String message = ex.getMostSpecificCause().getMessage();
        
        Map<String, Object> errorData = Map.of(
                "code", HttpStatus.BAD_REQUEST.value(),
                "status", HttpStatus.BAD_REQUEST.name(),
                "message", "Format JSON invalide ou valeur incorrecte (type ou taille)",
                "details", message
        );
        
        ApiResponse<Map<String, Object>> response = ApiResponse.<Map<String, Object>>builder()
                .message("Format JSON invalide")
                .date(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .data(errorData)
                .build();
        
        return ResponseEntity.badRequest().body(response);
    }
} 