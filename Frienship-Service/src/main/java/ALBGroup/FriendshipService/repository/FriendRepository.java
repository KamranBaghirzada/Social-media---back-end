package ALBGroup.FriendshipService.repository;

import ALBGroup.FriendshipService.node.Friend;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface FriendRepository extends Neo4jRepository<Friend, Integer> {

    @Query("MATCH (sender:Friend {email: $senderEmail}), (receiver:Friend {email: $receiverEmail}) " +
            "CREATE (sender)-[:SENT_FRIENDSHIP {requestDate: timestamp()}]->(receiver) " +
            "CREATE (receiver)-[:RECEIVED_FRIENDSHIP {requestDate: timestamp()}]->(sender)")
    void sendFriendshipRequest(String senderEmail, String receiverEmail);

    @Query("MATCH (sender:Friend {email: $senderEmail})-[:SENT_FRIENDSHIP]->(receiver:Friend {email: $receiverEmail}) " +
            "CREATE (sender)-[:HAS_FRIEND]->(receiver) " +
            "WITH sender, receiver " +
            "MATCH (sender)-[sent:SENT_FRIENDSHIP]->(receiver) " +
            "DELETE sent")
    void acceptFriendshipRequest(String senderEmail, String receiverEmail);

    @Query("MATCH (sender:Friend {email: $senderEmail})-[:SENT_FRIENDSHIP]->(receiver:Friend {email: $receiverEmail}) " +
            "RETURN COUNT(*) > 0")
    boolean friendshipRequestExists(String senderEmail, String receiverEmail);
}
