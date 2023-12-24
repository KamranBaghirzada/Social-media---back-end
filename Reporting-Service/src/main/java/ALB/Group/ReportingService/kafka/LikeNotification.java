package ALB.Group.ReportingService.kafka;

import lombok.Data;

@Data
public class LikeNotification {

    private String fromUserEmail;
    private String toUserEmail;
    private String postId;
}
