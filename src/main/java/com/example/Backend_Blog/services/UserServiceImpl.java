package com.example.Backend_Blog.services;

import com.example.Backend_Blog.config.JwtProvider;
import com.example.Backend_Blog.model.Post;
import com.example.Backend_Blog.model.User;
import com.example.Backend_Blog.repository.PostRepository;
import com.example.Backend_Blog.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    @Override
    public User updateUser(User user, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return null; // Or handle it as you see fit
        }

        User existingUser = userOptional.get();
        if (user.getName() != null && !user.getName().trim().isEmpty())
            existingUser.setName(user.getName());
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty())
            existingUser.setEmail(user.getEmail());
        if (user.getPassword() != null && !user.getPassword().isEmpty())
            existingUser.setPassword(user.getPassword());
        if (user.getProfileImg() != null && !user.getProfileImg().trim().isEmpty())
            existingUser.setProfileImg(user.getProfileImg());

        return userRepository.save(existingUser);
    }

    @Override
    public User findUserByJwt(String token) {
        String email = JwtProvider.getEmailFromJwtToken(token);
        return userRepository.findByEmail(email);
    }
    @Override
    public List<Post> getSavedPosts(User user)throws EntityNotFoundException {
       if(user==null)
           throw  new EntityNotFoundException("User not found");
        return user.getSavedPost();
    }

    @Override
    public void savePost(User user, Long postId) {
        if(user==null)
            throw new EntityNotFoundException("user not found");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!user.getSavedPost().contains(post)) {
            user.getSavedPost().add(post);
            userRepository.save(user);
        }
    }

    @Override
    public void removeSavedPost(User user, Long postId) {
        if(user==null)
            throw new EntityNotFoundException("user not found");
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
            userRepository.save(user);
        }
    }
}
