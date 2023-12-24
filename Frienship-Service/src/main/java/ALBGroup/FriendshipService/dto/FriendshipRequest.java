package ALBGroup.FriendshipService.dto;

import lombok.Data;

@Data
public class FriendshipRequest {

    private String senderEmail;

    private String receiverEmail;
}
