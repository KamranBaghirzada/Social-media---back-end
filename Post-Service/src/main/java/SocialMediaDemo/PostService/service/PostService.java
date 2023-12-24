package SocialMediaDemo.PostService.service;

import SocialMediaDemo.PostService.dto.AuthorizationResponse;
import SocialMediaDemo.PostService.dto.PostRequest;
import SocialMediaDemo.PostService.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface PostService {

    Page<PostResponse> getAllPosts(Pageable pageable);

    PostResponse getPostById(String postId);

    Page<PostResponse> getPostsOfUser(String postId, Pageable pageable);

    String addPost(AuthorizationResponse response, String text, MultipartFile multipartFile) throws IOException;

    PostResponse updatePost(String postId, PostRequest request);

    void deletePost(String postId);
}
