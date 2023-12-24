package ALB.Group.ReportingService.repository;

import ALB.Group.ReportingService.entity.model.Like;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface LikeRepository extends CassandraRepository<Like, UUID> {
}
