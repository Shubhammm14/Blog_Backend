package com.example.Backend_Blog.services;

import com.example.Backend_Blog.model.Post;
import com.example.Backend_Blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setDescription(updatedPost.getDescription());
            existingPost.setPostImg(updatedPost.getPostImg());
            existingPost.setContentUrl(updatedPost.getContentUrl());
            return postRepository.save(existingPost);
        }
        return null;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    @Override
    public List<Post> searchPosts(String keyword) {
        return postRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
}
