package com.example.Backend_Blog.services;



import com.example.Backend_Blog.exception.UserExcepition;
import com.example.Backend_Blog.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public User findUserById(Long userId);
    public User findUserByEmail(String email);
    public User updateUser(User user, Long userId) throws UserExcepition;
    public User findUserByJwt( String token);
}
