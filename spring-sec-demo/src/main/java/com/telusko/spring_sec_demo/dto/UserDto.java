package com.telusko.spring_sec_demo.dto;

import com.telusko.spring_sec_demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String username;
    private String password;

    public User toUser()
    {
        return User.builder().username(this.username).password(this.password).build();
    }
}
