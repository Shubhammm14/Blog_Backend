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
            if(updatedPost.getTitle()!=null)
            existingPost.setTitle(updatedPost.getTitle());
            if(updatedPost.getDescription()!=null)
            existingPost.setDescription(updatedPost.getDescription());
            if(updatedPost.getPostImg()!=null)
            existingPost.setPostImg(updatedPost.getPostImg());
            if(updatedPost.getContentUrl()!=null)
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
    @Override
    public List<Post> getPostsByAuthorId(Long authorId) {
        return postRepository.findByAuthorId(authorId);
    }
}
