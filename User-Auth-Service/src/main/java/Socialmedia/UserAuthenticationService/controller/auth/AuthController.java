package Socialmedia.UserAuthenticationService.controller.auth;

import Socialmedia.UserAuthenticationService.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Socialmedia.UserAuthenticationService.service.auth.RegistrationAndAuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final RegistrationAndAuthenticationService registrationAndAuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationAndRegistrationResponse> register(@Valid @RequestBody  UserRegRequest request) {
        return ResponseEntity.ok(registrationAndAuthenticationService.registerUser(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationAndRegistrationResponse> register(@Valid @RequestBody UserAuthenticationRequest request) {
        return ResponseEntity.ok(registrationAndAuthenticationService.authenticateUser(request));
    }

    @PostMapping("/authorize")
    public ResponseEntity<UserAuthorizationResponse> authorize(@RequestBody UserAuthorizationRequest request) {
        return ResponseEntity.ok(registrationAndAuthenticationService.authorizeUser(request));
    }
}
