package SocialMediaDemo.PostService.repository;

import SocialMediaDemo.PostService.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findByUserId(String userId, Pageable pageable);
}
