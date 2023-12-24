package ALB.Group.ReportingService.service.impl;

import ALB.Group.ReportingService.entity.model.Comment;
import ALB.Group.ReportingService.entity.model.Friendship;
import ALB.Group.ReportingService.entity.model.Like;
import ALB.Group.ReportingService.kafka.CommentNotification;
import ALB.Group.ReportingService.kafka.FriendshipNotification;
import ALB.Group.ReportingService.kafka.LikeNotification;
import ALB.Group.ReportingService.repository.CommentRepository;
import ALB.Group.ReportingService.repository.FriendshipRepository;
import ALB.Group.ReportingService.repository.LikeRepository;
import ALB.Group.ReportingService.service.abs.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {


    private final FriendshipRepository friendshipRepository;

    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    @Override
    public void saveFriend(FriendshipNotification notification) {
        Friendship friendship = new Friendship();
        friendship.setId(UUID.randomUUID());
        friendship.setSenderEmail(notification.getFromUserEmail());
        friendship.setReceiverEmail(notification.getToUserEmail());
        friendship.setRequestTime(LocalDateTime.now());
        friendshipRepository.save(friendship);
    }

    @Override
    public void saveLike(LikeNotification notification) {
        Like like = new Like();
        like.setLikedUser(notification.getFromUserEmail());
        like.setPostSharedUser(notification.getToUserEmail());
        like.setPostId(notification.getPostId());
        like.setId(UUID.randomUUID());
        like.setLikedTime(LocalDateTime.now());
        likeRepository.save(like);
    }

    @Override
    public void saveComment(CommentNotification notification) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setPostId(notification.getPostId());
        comment.setCommentedUser(notification.getFromUserEmail());
        comment.setPostSharedUser(notification.getToUserEmail());
        comment.setCommentedTime(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
