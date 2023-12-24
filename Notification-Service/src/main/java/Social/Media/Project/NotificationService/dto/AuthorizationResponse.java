package Social.Media.Project.NotificationService.dto;

import lombok.Data;

@Data
public class AuthorizationResponse {

    private Boolean hasAccess;

    private String email;

    private Long userId;
}
