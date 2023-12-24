package ALB.Group.ReportingService.repository;

import ALB.Group.ReportingService.entity.model.Comment;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface CommentRepository extends CassandraRepository<Comment, UUID> {
}
