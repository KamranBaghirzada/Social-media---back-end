package SocialMediaDemo.PostService.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PostResponse {

    private String userId;
    private String text;
    private String fileId;
    private String imageUrl;
    private String videoUrl;
    private String audioUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedDate;
}
