package ALBGroup.FriendshipService.service;

import ALBGroup.FriendshipService.dto.FriendshipRequest;
import ALBGroup.FriendshipService.dto.RegisterRequest;

public interface FriendService {

    void registerUser(RegisterRequest request);

    void sendFriendshipRequest(String  senderEmail, String receiverEmail);

    void acceptFriendshipRequest(String  senderEmail, String receiverEmail);
}
