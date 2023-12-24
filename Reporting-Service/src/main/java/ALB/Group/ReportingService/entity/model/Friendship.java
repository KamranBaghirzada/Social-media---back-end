package ALB.Group.ReportingService.entity.model;

import ALB.Group.ReportingService.entity.enums.RequestType;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Data
public class Friendship {

    @PrimaryKey
    private UUID id;

    private String senderEmail;

    private String receiverEmail;

    private LocalDateTime requestTime;

    private RequestType requestType;
}
