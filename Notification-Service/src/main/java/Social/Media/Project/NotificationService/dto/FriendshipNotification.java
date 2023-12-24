package Social.Media.Project.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipNotification {

    private String fromUserEmail;
    private String toUserEmail;
}
