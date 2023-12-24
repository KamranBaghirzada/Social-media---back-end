package ALB.Group.ReportingService.kafka;

import lombok.Data;

@Data
public class FriendshipNotification {

    private String fromUserEmail;
    private String toUserEmail;
}
