package com.telusko.spring_sec_demo.controllers;

import com.telusko.spring_sec_demo.dto.GetUserResponse;
import com.telusko.spring_sec_demo.dto.UserDto;
import com.telusko.spring_sec_demo.model.User;
import com.telusko.spring_sec_demo.service.JwtService;
import com.telusko.spring_sec_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;
    @PostMapping("/register")
    public GetUserResponse register(@RequestBody UserDto userDto)
    {
        System.out.println("Hello "+userDto.getUsername()+" "+userDto.getPassword());
        User user=userService.findByUsername(userDto.getUsername());
        if(user==null)
        {
            return userService.createUser(userDto.toUser());
        }
        System.out.println("Please provide unique username");
        return null;
    }
    //Actually the request goes from AuthenticationManager to AuthenticationProvider
    //In this method we are checking the User credentials if that valids we will generate the token for an User.
    @PostMapping("/login")
    public String login(@RequestBody UserDto userDto)
    {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword()));
        if(authentication.isAuthenticated())
           return jwtService.generateToken(userDto.getUsername());
        else
            return "login Failed";
    }
}
