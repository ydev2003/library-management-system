package com.merck.library_management_system.exception;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

public enum LmsErrorCodes {

    INTERNAL_SERVER_ERROR("Internal Server Error", "Server encountered an unexpected condition that prevented it from fulfilling the request"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found with username {0}"),
    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", "User already exists with username {0}"),
    BOOK_NOT_FOUND("BOOK_NOT_FOUND", "Book not found with title {0}"),
    BOOK_ALREADY_EXISTS("BOOK_ALREADY_EXISTS", "Book already exists with title {0}"),
    AUTHENTICATION_FAILED("AUTHENTICATION_FAILED", "Authentication failed for user {0}"),
    ;


    private final String errorCode;

    private final String description;

    LmsErrorCodes(String errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public static String getFormatedMessage(LmsErrorCodes lmsErrorCodes, List<?> params) {
        Object[] objArray = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            objArray[i] = params.get(i);
        }
        return MessageFormat.format(lmsErrorCodes.getDescription(), objArray);
    }

    public static String getFormatedCode(LmsErrorCodes lmsErrorCodes, List<String> params) {
        Object[] objArray = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            objArray[i] = params.get(i).toUpperCase().replace("", "_");
        }
        return MessageFormat.format(lmsErrorCodes.getDescription(), objArray);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
