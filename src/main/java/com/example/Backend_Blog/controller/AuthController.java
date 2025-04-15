package com.example.Backend_Blog.controller;


import com.example.Backend_Blog.config.JwtProvider;
import com.example.Backend_Blog.exception.UserExcepition;
import com.example.Backend_Blog.model.User;
import com.example.Backend_Blog.repository.UserRepository;
import com.example.Backend_Blog.request.LoginRequest;
import com.example.Backend_Blog.response.AuthResponse;
import com.example.Backend_Blog.services.CustomUserDetailService;
import com.example.Backend_Blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping("/signup")
    public AuthResponse createUser(@RequestBody User user)throws UserExcepition{
        User isExist=userRepository.findByEmail((user.getEmail()));
        if(isExist!=null)
        {
            throw new UserExcepition("this email already used with another account");
        }
        User newUser=new User(user.getEmail(),user.getName(),passwordEncoder.encode(user.getPassword()));
        User  savedUser=userRepository.save(newUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(),savedUser.getPassword());
        String token= JwtProvider.generateToken(authentication);
        return new AuthResponse(token,"validation done");
    }
    @PostMapping("/sign_in")
    public AuthResponse signIn(@RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication= authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        String token=JwtProvider.generateToken(authentication);
        return new AuthResponse(token,"validation done");
    }
    private Authentication authenticate(String email,String password) throws Exception {
        UserDetails userDetails=customUserDetailService.loadUserByUsername(email);
        if(userDetails==null)
            throw new BadCredentialsException("Invalid email");
        if (!passwordEncoder.matches(password,userDetails.getPassword()))
            throw new BadCredentialsException(("Invalid password"));
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
