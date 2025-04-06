package com.merck.library_management_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

@ControllerAdvice
public class LmsHandler {

    private static final Map<String, HttpStatus> getStatusMapping;

    static {
        Map<String, HttpStatus> statusMapping = new HashMap<>();
        statusMapping.put(LmsErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        statusMapping.put(LmsErrorCodes.USER_NOT_FOUND.getErrorCode(), HttpStatus.NOT_FOUND);

        getStatusMapping = Collections.unmodifiableMap(statusMapping);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(LmsErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode());
        errorResponse.setErrorMessage(LmsErrorCodes.INTERNAL_SERVER_ERROR.getDescription() + exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(LmsException.class)
    public ResponseEntity<ErrorResponse> lmsException(LmsException lmsException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(lmsException.getErrorCode());
        errorResponse.setErrorMessage(lmsException.getMessage());
        return new ResponseEntity<>(errorResponse, getStatusMapping.get(lmsException.getErrorCode()));
    }

    @ExceptionHandler(DatavaidationException.class)
    public ResponseEntity<ErrorResponse> datavaidationException(DatavaidationException datavaidationException) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(datavaidationException.getErrorCode());
        errorResponse.setErrorMessage(datavaidationException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


}