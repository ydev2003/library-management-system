package com.merck.library_management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.merck.library_management_system.security.JwtUtil;
import com.merck.library_management_system.securitymodels.AuthenticationRequest;
import com.merck.library_management_system.securitymodels.AuthenticationResponse;
import com.merck.library_management_system.services.MyUserDetailsService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Authentication Operations", description = "Operations related to Authentication management")
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

//	@GetMapping({ "/hello" })
//	public String firstPage() {
//		return "Hello World";
//	}
	
	@ApiOperation(tags="Authentication Operations", value="Admin Login", notes = "Login as Admin")
	@PostMapping("/admin")
    public ResponseEntity<?> createAuthenticationTokenForAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return createAuthenticationToken(authenticationRequest, "ROLE_ADMIN");
    }
	

	@ApiOperation(tags="Authentication Operations", value = "Student Login", notes = "Login as Student")
    @PostMapping("/user")
    public ResponseEntity<?> createAuthenticationTokenForStudent(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return createAuthenticationToken(authenticationRequest, "ROLE_STUDENT");
    }
	
	
	

    private ResponseEntity<?> createAuthenticationToken(AuthenticationRequest authenticationRequest, String expectedRole) throws Exception {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        
        if (!userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(expectedRole))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: User does not have the required role");
        }
        final String jwt = jwtTokenUtil.generateToken(userDetails, expectedRole);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
