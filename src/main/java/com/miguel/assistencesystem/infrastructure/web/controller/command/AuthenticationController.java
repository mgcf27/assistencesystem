package com.miguel.assistencesystem.infrastructure.web.controller.command;

import com.miguel.assistencesystem.application.dto.command.LoginRequestDTO;
import com.miguel.assistencesystem.application.dto.response.LoginResponseDTO;
import com.miguel.assistencesystem.application.security.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(
            AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(
            @RequestBody LoginRequestDTO request
    ) {

        return authenticationService.login(request);

    }
    
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        authenticationService.logOut();
    }
}
