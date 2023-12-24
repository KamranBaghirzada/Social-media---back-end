package SocialMediaDemo.PostService.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PostRequest {

    private String userId;
    private String text;
}
