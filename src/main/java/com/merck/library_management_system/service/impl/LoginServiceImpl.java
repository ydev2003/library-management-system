package com.merck.library_management_system.service.impl;

import com.merck.library_management_system.model.AuthenticationRequest;
import com.merck.library_management_system.security.JwtUtil;
import com.merck.library_management_system.service.LoginService;
import com.merck.library_management_system.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author Devesh Yadav
 * @date 06-04-2025
 * @project library-management-system
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final MyUserDetailsService userDetailsService;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtTokenUtil, MyUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }


    @Override
    public String createAuthenticationToken(AuthenticationRequest authenticationRequest, String expectedRole) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (!userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(expectedRole))) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: User does not have the required role");
        }
        return jwtTokenUtil.generateToken(userDetails, expectedRole);
    }
}
