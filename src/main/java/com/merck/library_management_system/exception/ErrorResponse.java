package com.merck.library_management_system.exception;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

public class ErrorResponse {

    private String errorMessage;

    private String errorCode;

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
