package SocialMediaDemo.PostService.external;

import SocialMediaDemo.PostService.dto.AuthorizationResponse;
import SocialMediaDemo.PostService.dto.AuthorizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizeService {

    private final ExternalAuthorizeService externalAuthorizeService;

    public AuthorizationResponse checkAuthority(String authorizationHeader, String role) {
        AuthorizeRequest authorizeRequest = getAuthorizeRequest(authorizationHeader, role);
        return externalAuthorizeService.authorizeUser(authorizeRequest);
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
