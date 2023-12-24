package ALBGroup.FriendshipService.node;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friend {

    @Id
    private Integer id;

    @Property(name = "email")
    private String email;

    @Relationship(type = "HAS_FRIEND")
    private Set<Friend> friends = new HashSet<>();

    @Relationship(type = "SENT_FRIENDSHIP")
    private Set<Friendship> sentFriendships = new HashSet<>();

    @Relationship(type = "RECEIVED_FRIENDSHIP")
    private Set<Friendship> receivedFriendships = new HashSet<>();
}
