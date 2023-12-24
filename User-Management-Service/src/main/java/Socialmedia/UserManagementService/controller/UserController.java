package Socialmedia.UserManagementService.controller;

import Socialmedia.UserManagementService.dto.AuthorizeRequest;
import Socialmedia.UserManagementService.dto.UserRequest;
import Socialmedia.UserManagementService.dto.UserResponse;
import Socialmedia.UserManagementService.exception.UnauthorizedAccessException;
import Socialmedia.UserManagementService.external.AuthorizeService;
import Socialmedia.UserManagementService.external.ExternalAuthorizeService;
import Socialmedia.UserManagementService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final AuthorizeService authorizeService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getUser(@RequestHeader(name = "Authorization") String authorizationHeader, @ModelAttribute UserRequest criteria) {
        Boolean hasAccess = authorizeService.checkAuthority(authorizationHeader,"USER");
        if (hasAccess) {
            return ResponseEntity.ok(userService.getUser(criteria));
        } else {
            throw new UnauthorizedAccessException("You don't have permission to access this resource.");
        }
    }
}
