package Social.Media.Project.NotificationService.controller;

import Social.Media.Project.NotificationService.dto.*;
import Social.Media.Project.NotificationService.external.ExternalAuthorizeService;
import Social.Media.Project.NotificationService.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    private final ExternalAuthorizeService externalAuthorizeService;

    @PostMapping("/friend-request")
    public ResponseEntity<?> sendFriendRequest(@RequestBody FriendshipNotification request) {
        notificationService.sendFriendRequestNotification(request.getFromUserEmail(), request.getToUserEmail());
        return ResponseEntity.ok("Friend request notification sent");
    }

    @PostMapping("/like")
    public ResponseEntity<?> sendLikeNotification(@RequestBody LikeNotification request) {
        notificationService.sendLikeNotification(request.getFromUserEmail(), request.getToUserEmail(), request.getPostId());
        return ResponseEntity.ok("Like notification sent");
    }

    @PostMapping("/comment")
    public ResponseEntity<?> sendCommentNotification(@RequestBody CommentNotification request) {
        notificationService.sendCommentNotification(request.getFromUserEmail(), request.getToUserEmail(), request.getPostId(), request.getComment());
        return ResponseEntity.ok("Comment notification sent");
    }

    @PostMapping("/test")
    public ResponseEntity<AuthorizationResponse> test(@RequestBody AuthorizeRequest request) {
        return ResponseEntity.ok(externalAuthorizeService.authorizeUser(request));
    }
}
