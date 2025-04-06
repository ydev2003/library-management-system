package com.merck.library_management_system.exception;

import java.util.List;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

public class LmsException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String errorCode;

    public LmsException(String message) {
        super(message);
        this.errorCode = "";
    }

    public LmsException(LmsErrorCodes lmsErrorCodes) {
        super(lmsErrorCodes.getDescription());
        this.errorCode = lmsErrorCodes.getErrorCode();
    }

    public LmsException(LmsErrorCodes lmsErrorCodes, List<?> params) {
        super(String.format(lmsErrorCodes.getDescription(), params));
        this.errorCode = lmsErrorCodes.getErrorCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
