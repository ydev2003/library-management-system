package com.merck.library_management_system.controller;

import com.merck.library_management_system.model.AuthenticationRequest;
import com.merck.library_management_system.service.LoginService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Authentication Operations", description = "Operations related to Authentication management")
@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Will be used to log in as Admin
     *
     * @param authenticationRequest - authentication request
     * @return jwt token
     */
    @PostMapping("/admin")
    @ApiOperation(tags = "Authentication Operations", value = "Admin Login", notes = "Login as Admin")
    public String createAuthenticationTokenForAdmin(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return loginService.createAuthenticationToken(authenticationRequest, "ROLE_ADMIN");
    }

    /**
     * Will be used to log in as student
     *
     * @param authenticationRequest - authentication request
     * @return - jwt token
     * @throws - Exception
     */
    @PostMapping("/user")
    @ApiOperation(tags = "Authentication Operations", value = "Student Login", notes = "Login as Student")
    public String createAuthenticationTokenForStudent(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return loginService.createAuthenticationToken(authenticationRequest, "ROLE_STUDENT");
    }
}
