package com.example.Backend_Blog.controller;

import com.example.Backend_Blog.model.Post;
import com.example.Backend_Blog.services.PostService;
import com.example.Backend_Blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    // Create a new post
    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody Post post,@RequestHeader("Authorization") String token) {
        if (post.getTitle() == null || post.getDescription() == null ||
                post.getPostImg() == null || post.getContentUrl() == null) {
            return ResponseEntity.badRequest().body("All fields (title, description, postImg, contentUrl) are required.");
        }
        post.setAuthor(userService.findUserByJwt(token));
        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }


    // Get a single post by ID
    @GetMapping("posts/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // Get all posts
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    // Update a post
    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post updated = postService.updatePost(id, post);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam String keyword) {
        List<Post> results = postService.searchPosts(keyword);
        return ResponseEntity.ok(results);
    }
    // Delete a post
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,@RequestHeader("Authorization") String token) {
        Post post=postService.getPostById(id).get();
        if(!Objects.equals(post.getAuthor().getId(), userService.findUserByJwt(token).getId()))
            return ResponseEntity.badRequest().build();
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/api/posts/users")
    public List<Post> getPostsByAuthorId(@RequestHeader("Authorization") String token) {
        return postService.getPostsByAuthorId(userService.findUserByJwt(token).getId());
    }

}
