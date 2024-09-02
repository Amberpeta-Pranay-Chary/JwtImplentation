package com.telusko.spring_sec_demo.service;

import com.telusko.spring_sec_demo.configs.Constants;
import com.telusko.spring_sec_demo.configs.PasswordEncoder;
import com.telusko.spring_sec_demo.dto.GetUserResponse;
import com.telusko.spring_sec_demo.model.User;
import com.telusko.spring_sec_demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    public User findByUsername(String username)
    {
        Optional<User> userOptional= userRepo.findByUserName(username);
        if(userOptional.isEmpty())
            return null;
        return userOptional.get();
    }

    public GetUserResponse createUser(User user) {
        user.setAuthorities(Constants.NORMAL_USER);
        user.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(user.getPassword()));
       User user1=userRepo.save(user);
       return new GetUserResponse(user1);
    }
}
