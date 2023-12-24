package ALB.Group.ReportingService.service.abs;

import ALB.Group.ReportingService.kafka.CommentNotification;
import ALB.Group.ReportingService.kafka.FriendshipNotification;
import ALB.Group.ReportingService.kafka.LikeNotification;

public interface NotificationService {

    void saveFriend(FriendshipNotification notification);

    void saveLike(LikeNotification notification);

    void saveComment(CommentNotification notification);
}
