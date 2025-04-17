package com.example.Backend_Blog.controller;


import com.example.Backend_Blog.exception.UserExcepition;
import com.example.Backend_Blog.model.Post;
import com.example.Backend_Blog.model.User;
import com.example.Backend_Blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user by ID

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update user details
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) throws UserExcepition {
        User updatedUser = userService.updateUser(user, userId);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Get user by JWT token
    @GetMapping("/me")
    public ResponseEntity<User> getUserByJwt(@RequestHeader("Authorization") String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        User user = userService.findUserByJwt(jwtToken);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/saved-posts")
    public ResponseEntity<List<Post>> getSavedPosts(@RequestHeader("Authorization")String token) {
        return new ResponseEntity<>(userService.getSavedPosts(userService.findUserByJwt(token)),HttpStatus.OK);
    }

    // ✅ Save a post
    @PostMapping("/save-post/{postId}")
    public void savePost(@RequestHeader("Authorization")String token, @PathVariable Long postId) {
        userService.savePost(userService.findUserByJwt(token), postId);
    }

    // ✅ Remove post from saved
    @DeleteMapping("/remove-post/{postId}")
    public void removeSavedPost(@RequestHeader("Authorization") String token, @PathVariable Long postId) {
        userService.removeSavedPost(userService.findUserByJwt(token), postId);
    }
}
