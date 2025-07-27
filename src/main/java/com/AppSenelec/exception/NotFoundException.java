package com.AppSenelec.exception;

import com.AppSenelec.dto.ErrorDto;
import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private final ErrorDto errorDto;
    private final HttpStatus status;

    public NotFoundException(String message) {
        super(message);
        this.status = HttpStatus.NOT_FOUND;
        this.errorDto = new ErrorDto("NOT_FOUND", message);
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public HttpStatus getStatus() {
        return status;
    }
} 