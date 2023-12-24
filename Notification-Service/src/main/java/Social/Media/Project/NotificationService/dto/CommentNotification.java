package Social.Media.Project.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentNotification {

    private String fromUserEmail;
    private String toUserEmail;
    private String postId;
    private String comment;
}
