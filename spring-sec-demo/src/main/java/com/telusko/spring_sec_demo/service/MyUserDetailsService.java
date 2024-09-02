package com.telusko.spring_sec_demo.service;

import com.telusko.spring_sec_demo.model.User;
import com.telusko.spring_sec_demo.model.UserPrinciple;
import com.telusko.spring_sec_demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepo.findByUserName(username);
        if(optionalUser.get()!=null)
        return new UserPrinciple(optionalUser.get());
        return null;
    }
}
