package Socialmedia.UserManagementService.external;

import Socialmedia.UserManagementService.dto.AuthorizationResponse;
import Socialmedia.UserManagementService.dto.AuthorizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final ExternalAuthorizeService externalAuthorizeService;

    public Boolean checkAuthority(String authorizationHeader, String role) {
        AuthorizeRequest authorizeRequest = getAuthorizeRequest(authorizationHeader, role);
        AuthorizationResponse authorizationResponse = externalAuthorizeService.authorizeUser(authorizeRequest);
        return authorizationResponse.getHasAccess();
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
