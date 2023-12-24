package Socialmedia.UserAuthenticationService.dto;

import lombok.Data;

@Data
public class UserAuthorizationRequest {

    private String jwtToken;

    private String role;
}
