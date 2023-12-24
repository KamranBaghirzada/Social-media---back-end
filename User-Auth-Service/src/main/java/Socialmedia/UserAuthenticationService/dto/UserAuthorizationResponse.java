package Socialmedia.UserAuthenticationService.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthorizationResponse {

    private Boolean hasAccess;

    private Integer userId;

    private String email;
}
