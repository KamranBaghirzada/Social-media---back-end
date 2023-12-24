package SocialMediaDemo.PostService.mapper;

import SocialMediaDemo.PostService.dto.PostRequest;
import SocialMediaDemo.PostService.dto.PostResponse;
import SocialMediaDemo.PostService.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponse postToPostResponse(Post post);

    Post postRequestToPost(PostRequest request);
}
