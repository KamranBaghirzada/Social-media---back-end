package ALB.Group.ReportingService.kafka;

import ALB.Group.ReportingService.service.abs.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatternNotificationConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final NotificationService notificationService;

    @KafkaListener(topicPattern = "albendemu_notifications")
    public void consumeNotifications(ConsumerRecord<String, String> record) {
        String key = record.key();
        String jsonMessage = record.value();

        try {
            switch (key) {
                case "friend_request":
                    FriendshipNotification friendshipNotification = objectMapper.readValue(jsonMessage, FriendshipNotification.class);
                    System.out.println("LISTENING TOPIC OF USER " + friendshipNotification.getToUserEmail());
                    handleFriendshipNotification(friendshipNotification);
                    break;
                case "like":
                    LikeNotification likeNotification = objectMapper.readValue(jsonMessage, LikeNotification.class);
                    handleLikeNotification(likeNotification);
                    break;
                case "comment":
                    CommentNotification commentNotification = objectMapper.readValue(jsonMessage, CommentNotification.class);
                    handleCommentNotification(commentNotification);
                    break;
                default:
                    System.out.println("FETCHING!!!!!!!!!");
            }
        } catch (Exception e) {
            // Handle deserialization exceptions
        }
    }

    private void handleFriendshipNotification(FriendshipNotification notification) {
        notificationService.saveFriend(notification);
    }

    private void handleLikeNotification(LikeNotification notification) {
        notificationService.saveLike(notification);
    }

    private void handleCommentNotification(CommentNotification notification) {
        notificationService.saveComment(notification);
    }
}
