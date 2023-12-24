package ALBGroup.FriendshipService.controller;

import ALBGroup.FriendshipService.dto.AuthorizationResponse;
import ALBGroup.FriendshipService.dto.FriendshipRequest;
import ALBGroup.FriendshipService.dto.RegisterRequest;
import ALBGroup.FriendshipService.exception.UnauthorizedAccessException;
import ALBGroup.FriendshipService.external.AuthorizeService;
import ALBGroup.FriendshipService.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendService friendshipService;

    private final AuthorizeService authorizeService;

    @PostMapping("/register")
    public void registerNewUser(@RequestBody RegisterRequest request) {
        friendshipService.registerUser(request);
    }

    @PostMapping("/send-request")
    public void sendFriendshipRequest(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody String receiverEmail) {
        AuthorizationResponse authorizationResponse = getAuthorizationResponse(authorizationHeader);
        if (authorizationResponse.getHasAccess()) {
            friendshipService.sendFriendshipRequest(authorizationResponse.getEmail(), receiverEmail);
        } else {
            throw new UnauthorizedAccessException("You do not have access. ");
        }
    }

    @PostMapping("/accept-request")
    public void acceptFriendshipRequest(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody String receiverEmail) {
        AuthorizationResponse authorizationResponse = getAuthorizationResponse(authorizationHeader);
        if (authorizationResponse.getHasAccess()) {
            friendshipService.acceptFriendshipRequest(authorizationResponse.getEmail(), receiverEmail);
        } else {
            throw new UnauthorizedAccessException("You do not have access. ");
        }
    }

    private AuthorizationResponse getAuthorizationResponse(String authorizationHeader) {
        return authorizeService.getAuthorizationResponse(authorizationHeader, "USER");
    }
}
