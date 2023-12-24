package SocialMediaDemo.PostService.service;

import SocialMediaDemo.PostService.dto.AuthorizationResponse;
import SocialMediaDemo.PostService.dto.PostRequest;
import SocialMediaDemo.PostService.dto.PostResponse;
import SocialMediaDemo.PostService.exception.PostNotFoundException;
import SocialMediaDemo.PostService.mapper.PostMapper;
import SocialMediaDemo.PostService.model.Post;
import SocialMediaDemo.PostService.repository.PostRepository;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    private final GridFsTemplate gridFsTemplate;

    private final ServiceUtil serviceUtil;

    @Override
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Pageable pageAndSort = serviceUtil.pageableWithSorting(pageable);
        Page<Post> postPage = postRepository.findAll(pageAndSort);

        List<PostResponse> responseList = postPage.stream()
                .map(postMapper::postToPostResponse)
                .toList();

        return new PageImpl<>(responseList, pageAndSort, responseList.size());
    }

    @Override
    public PostResponse getPostById(String postId) {
        Post post = fetchPostById(postId);
        return postMapper.postToPostResponse(post);
    }

    @Override
    public Page<PostResponse> getPostsOfUser(String postId, Pageable pageable) {
        Pageable pageAndSort = serviceUtil.pageableWithSorting(pageable);
        Page<Post> postPage = postRepository.findByUserId(postId, pageAndSort);

        List<PostResponse> responseList = postPage.stream()
                .map(postMapper::postToPostResponse)
                .toList();

        return new PageImpl<>(responseList, pageAndSort, responseList.size());
    }

    @Override
    @Transactional
    public String addPost(AuthorizationResponse authResponse, String text, MultipartFile upload) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("fileSize", upload.getSize());
        Object fileID = gridFsTemplate.store(upload.getInputStream(), upload.getOriginalFilename(), upload.getContentType(), metadata);

        Post post = new Post();
        post.setFileId(fileID.toString());
        post.setText(text);
        post.setUserId(authResponse.getUserId().toString());
        postRepository.save(post);
        return fileID.toString();
    }

    @Override
    public PostResponse updatePost(String postId, PostRequest request) {
        Post post = fetchPostById(postId);
        post.setText(request.getText());
        post.setUserId(request.getUserId());

        postRepository.save(post);

        return postMapper.postToPostResponse(post);
    }

    @Override
    public void deletePost(String postId) {
        postRepository.deleteById(postId);
    }

    private Post fetchPostById(String postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with id: " + postId + " does not exist . "));
    }
}
