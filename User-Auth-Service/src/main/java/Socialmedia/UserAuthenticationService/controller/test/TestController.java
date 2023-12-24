package Socialmedia.UserAuthenticationService.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/test-secured-endpoint")
public class TestController {

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> testSecuredEndpoint() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println("User roles from @PreAuthorize: " + authorities);
        return ResponseEntity.ok("You had successfully authenticated. ");
    }
}
