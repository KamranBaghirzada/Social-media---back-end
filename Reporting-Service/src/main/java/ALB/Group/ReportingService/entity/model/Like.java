package ALB.Group.ReportingService.entity.model;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Data
public class Like {

    @PrimaryKey
    private UUID id;

    private String postId;

    private String postSharedUser;

    private String likedUser;

    private LocalDateTime likedTime;
}
