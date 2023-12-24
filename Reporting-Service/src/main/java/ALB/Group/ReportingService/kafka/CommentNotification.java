package ALB.Group.ReportingService.kafka;

import lombok.Data;

@Data
public class CommentNotification {

    private String fromUserEmail;
    private String toUserEmail;
    private String postId;
}
