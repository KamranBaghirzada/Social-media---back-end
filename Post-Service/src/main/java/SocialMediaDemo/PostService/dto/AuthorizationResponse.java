package SocialMediaDemo.PostService.dto;

import lombok.Data;

@Data
public class AuthorizationResponse {

    private Boolean hasAccess;

    private Long userId;
}
