package SocialMediaDemo.PostService.controller;

import SocialMediaDemo.PostService.dto.AuthorizationResponse;
import SocialMediaDemo.PostService.dto.FileResponse;
import SocialMediaDemo.PostService.dto.PostRequest;
import SocialMediaDemo.PostService.dto.PostResponse;
import SocialMediaDemo.PostService.external.AuthorizeService;
import SocialMediaDemo.PostService.service.FileService;
import SocialMediaDemo.PostService.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final FileService fileService;

    private final AuthorizeService authorizeService;

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAllPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getAllPosts(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") String postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/files")
    public ResponseEntity<Page<FileResponse>> getAllFiles(Pageable pageable) throws IOException {
        return ResponseEntity.ok(fileService.getAllFiles(pageable));
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<FileResponse> getFileById(@PathVariable("fileId") String fileId) throws IOException {
        return fileService.getFileById(fileId)
                .map(data -> ResponseEntity.ok().body(data))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<PostResponse>> getPostsOfUser(@PathVariable("userId") String userId, Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsOfUser(userId, pageable));
    }

    @PostMapping
    public ResponseEntity<String> addPost(@RequestHeader(name = "Authorization") String authorizationHeader,
                                          @RequestParam String text,
                                          @RequestParam(required = false) MultipartFile multipartFile) throws IOException {
        AuthorizationResponse authResponse = authorizeService.checkAuthority(authorizationHeader, "USER");
        if (authResponse.getHasAccess()) {
            return ResponseEntity.ok(postService.addPost(authResponse, text, multipartFile));
        } else {
            throw new RuntimeException("You don't have permission to access this resource.");//TODO make it custom exception
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") String postId, @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") String postId) {
        postService.deletePost(postId);
    }
}
