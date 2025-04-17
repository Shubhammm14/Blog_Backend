package com.example.Backend_Blog.services;

import com.example.Backend_Blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface PostService {
    Post createPost(Post post);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    Optional<Post> getPostById(Long id);
    List<Post> searchPosts(String keyword);
    List<Post> getAllPosts();

    List<Post> getPostsByAuthorId(Long authorId);
}
