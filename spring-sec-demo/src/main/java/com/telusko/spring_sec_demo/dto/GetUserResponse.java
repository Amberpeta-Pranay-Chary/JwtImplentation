package com.telusko.spring_sec_demo.dto;

import com.telusko.spring_sec_demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class GetUserResponse {
    private String username;
    private String password;
    public GetUserResponse(User user)
    {
        this.username=user.getUsername();
        this.password=user.getPassword();
    }

}
