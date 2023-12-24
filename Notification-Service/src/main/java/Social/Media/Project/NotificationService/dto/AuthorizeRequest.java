package Social.Media.Project.NotificationService.dto;

import lombok.Data;

@Data
public class AuthorizeRequest {

    private String jwtToken;

    private String role;
}
