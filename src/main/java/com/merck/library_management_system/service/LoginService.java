package com.merck.library_management_system.service;

import com.merck.library_management_system.model.AuthenticationRequest;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */

public interface LoginService {


    String createAuthenticationToken(AuthenticationRequest authenticationRequest, String expectedRole);
}
