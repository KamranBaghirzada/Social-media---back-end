package ALBGroup.FriendshipService.node;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class Friendship {

    @Id
    private Long id;

    private LocalDateTime requestDate;
    private LocalDateTime acceptanceDate;
}
