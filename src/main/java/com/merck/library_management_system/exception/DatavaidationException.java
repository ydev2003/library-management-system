package com.merck.library_management_system.exception;

import java.io.Serial;
import java.util.List;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

public class DatavaidationException extends LmsException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DatavaidationException(String message) {
        super(message);
    }

    public DatavaidationException(LmsErrorCodes lmsErrorCodes) {
        super(lmsErrorCodes);
    }

    public DatavaidationException(LmsErrorCodes lmsErrorCodes, List<?> params) {
        super(lmsErrorCodes, params);
    }
}
