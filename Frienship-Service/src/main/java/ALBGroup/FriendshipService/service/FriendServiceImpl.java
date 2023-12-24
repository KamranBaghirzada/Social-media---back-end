package ALBGroup.FriendshipService.service;

import ALBGroup.FriendshipService.dto.FriendshipRequest;
import ALBGroup.FriendshipService.dto.RegisterRequest;
import ALBGroup.FriendshipService.exception.FriendshipRequestHasAlreadySentException;
import ALBGroup.FriendshipService.node.Friend;
import ALBGroup.FriendshipService.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendshipRepository;

    @Override
    public void registerUser(RegisterRequest request) {
        Friend newFriend = new Friend();
        newFriend.setEmail(request.getEmail());
        newFriend.setId(request.getUserId());
        friendshipRepository.save(newFriend);
    }

    @Override
    public void sendFriendshipRequest(String  senderEmail, String receiverEmail) {
        if (friendshipRepository.friendshipRequestExists(senderEmail, receiverEmail)) {
            throw new FriendshipRequestHasAlreadySentException("The friendship request already exists ");
        }
        friendshipRepository.sendFriendshipRequest(senderEmail, receiverEmail);
    }

    @Override
    public void acceptFriendshipRequest(String  senderEmail, String receiverEmail) {
        if (friendshipRepository.friendshipRequestExists(senderEmail, receiverEmail)) {
            throw new FriendshipRequestHasAlreadySentException("The friendship request already exists ");
        }
        friendshipRepository.acceptFriendshipRequest(senderEmail, receiverEmail);
    }
}
