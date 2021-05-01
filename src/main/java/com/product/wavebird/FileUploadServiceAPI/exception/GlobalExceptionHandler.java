package com.product.wavebird.FileUploadServiceAPI.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(PetAppException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(PetAppException pe){

        ErrorResponse errorResponse=new ErrorResponse();
        errorResponse.setError(pe.getLocalizedMessage());
        errorResponse.setMessage(pe.getMessage());
        errorResponse.setStatus(pe.getStatus());
        errorResponse.setTimestamp(new Date());
        return ResponseEntity.status(pe.getStatus()).body(errorResponse);
    }

}
