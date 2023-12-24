package Socialmedia.UserAuthenticationService.service.auth;

import Socialmedia.UserAuthenticationService.dto.*;


public interface RegistrationAndAuthenticationService {

    AuthenticationAndRegistrationResponse registerUser(UserRegRequest request);

    AuthenticationAndRegistrationResponse authenticateUser(UserAuthenticationRequest request);

    UserAuthorizationResponse authorizeUser(UserAuthorizationRequest request);
}