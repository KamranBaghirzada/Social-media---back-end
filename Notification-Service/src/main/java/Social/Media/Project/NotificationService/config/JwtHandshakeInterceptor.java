package Social.Media.Project.NotificationService.config;

import Social.Media.Project.NotificationService.dto.AuthorizationResponse;
import Social.Media.Project.NotificationService.exception.UnauthorizedUserException;
import Social.Media.Project.NotificationService.external.AuthorizeService;
import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final AuthorizeService authorizeService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String jwtToken = extractToken(request);
        if (jwtToken != null) {
            try {
                AuthorizationResponse authResponse = authorizeService.checkAuthority(jwtToken, "USER");
                if (authResponse != null) {
                    String username = authResponse.getEmail();
                    Principal userPrincipal = new UserPrincipal(username);
                    attributes.put("user", userPrincipal);
                    return true;
                }
            } catch (Exception e) {
                throw new UnauthorizedUserException("User does not have access to this resource. ");
            }
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

    private String extractToken(ServerHttpRequest request) {
        // First, try to get the token from the Authorization header
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        // If not in the header, try to get it from the query parameters
        return Optional.ofNullable(request.getURI().getQuery())
                .map(query -> URLDecoder.decode(query, StandardCharsets.UTF_8)) // Decode the entire query string
                .map(query -> query.split("&"))
                .flatMap(params -> Arrays.stream(params)
                        .filter(param -> param.startsWith("token="))
                        .map(param -> param.split("=")[1])
                        .findFirst())
                .orElse(null);
    }
}
