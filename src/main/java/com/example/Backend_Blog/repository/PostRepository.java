package com.example.Backend_Blog.repository;

import com.example.Backend_Blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId);
    List<Post> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String title, String description);
}
