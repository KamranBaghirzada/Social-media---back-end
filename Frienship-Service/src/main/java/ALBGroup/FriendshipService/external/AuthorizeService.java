package ALBGroup.FriendshipService.external;

import ALBGroup.FriendshipService.dto.AuthorizationResponse;
import ALBGroup.FriendshipService.dto.AuthorizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final ExternalAuthorizeService externalAuthorizeService;

    public AuthorizationResponse getAuthorizationResponse(String authorizationHeader, String role) {
        AuthorizeRequest authorizeRequest = getAuthorizeRequest(authorizationHeader, role);
        AuthorizationResponse authorizationResponse = externalAuthorizeService.authorizeUser(authorizeRequest);
        return authorizationResponse;
    }

    private AuthorizeRequest getAuthorizeRequest(String authorizationHeader, String role) {
        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        String jwtToken = extractBearerToken(authorizationHeader);
        authorizeRequest.setJwtToken(jwtToken);
        authorizeRequest.setRole(role);
        return authorizeRequest;
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
