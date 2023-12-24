package ALBGroup.FriendshipService.dto;

import lombok.Data;

@Data
public class AuthorizeRequest {

    private String jwtToken;

    private String role;
}
