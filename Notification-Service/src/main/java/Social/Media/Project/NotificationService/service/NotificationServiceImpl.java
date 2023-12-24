package Social.Media.Project.NotificationService.service;

import Social.Media.Project.NotificationService.dto.CommentNotification;
import Social.Media.Project.NotificationService.dto.FriendshipNotification;
import Social.Media.Project.NotificationService.dto.LikeNotification;
import Social.Media.Project.NotificationService.exception.NotificationSerializationException;
import Social.Media.Project.NotificationService.exception.TopicCreationException;
import Social.Media.Project.NotificationService.service.kafka.KafkaTopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicService kafkaTopicService;
    private final ObjectMapper objectMapper;

    public void sendFriendRequestNotification(String fromUserEmail, String toUserEmail) {
        String topicName = extractUsername(toUserEmail) + "_notifications";
        ensureTopicExists(topicName);
        String notificationJson = createNotificationJson(new FriendshipNotification(fromUserEmail, toUserEmail));
        kafkaTemplate.send(new ProducerRecord<>(topicName, "friend_request", notificationJson));
    }

    public void sendLikeNotification(String fromUserEmail, String toUserEmail, String postId) {
        String topicName = extractUsername(toUserEmail) + "_notifications";
        ensureTopicExists(topicName);
        String notificationJson = createNotificationJson(new LikeNotification(fromUserEmail, toUserEmail, postId));
        kafkaTemplate.send(new ProducerRecord<>(topicName, "like", notificationJson));
    }

    public void sendCommentNotification(String fromUserEmail, String toUserEmail, String postId, String comment) {
        String topicName = extractUsername(toUserEmail) + "_notifications";
        ensureTopicExists(topicName);
        String notificationJson = createNotificationJson(new CommentNotification(fromUserEmail, toUserEmail, postId, comment));
        kafkaTemplate.send(new ProducerRecord<>(topicName, "comment", notificationJson));
    }

    private void ensureTopicExists(String topicName) {
        int partitions = 3;
        short replicationFactor = 1;
        try {
            kafkaTopicService.createTopicIfNotExists(topicName, partitions, replicationFactor);
        } catch (Exception e) {
            throw new TopicCreationException("Topic with such name or count of partition and replication could not be created");
        }
    }

    private String extractUsername(String email) {
        return email.split("@")[0];
    }

    private String createNotificationJson(Object notification) {
        try {
            return objectMapper.writeValueAsString(notification);
        } catch (Exception e) {
            throw new NotificationSerializationException("Failed to serialize notification");
        }
    }
}
