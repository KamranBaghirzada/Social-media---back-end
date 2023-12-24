package Social.Media.Project.NotificationService.service;

public interface NotificationService {

    void sendFriendRequestNotification(String fromUserEmail, String toUserEmail);

    void sendLikeNotification(String fromUserEmail, String toUserEmail, String postId);

    void sendCommentNotification(String fromUserEmail, String toUserEmail, String postId, String comment);
}
