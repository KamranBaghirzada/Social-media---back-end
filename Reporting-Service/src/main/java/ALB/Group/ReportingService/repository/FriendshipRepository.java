package ALB.Group.ReportingService.repository;

import ALB.Group.ReportingService.entity.model.Friendship;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface FriendshipRepository extends CassandraRepository<Friendship, UUID> {
}
