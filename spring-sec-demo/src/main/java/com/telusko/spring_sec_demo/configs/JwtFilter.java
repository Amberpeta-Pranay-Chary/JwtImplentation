package com.telusko.spring_sec_demo.configs;

import com.telusko.spring_sec_demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
//THis class extends onceperrequestfilter because for every request this filter will execute
//This Filter class will execute before the default usernamePasswordFilter by Spring boot
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    ApplicationContext context;

    //Here we are getting the token via request object and will verify
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userName = jwtService.extractUserName(token);
            System.out.println("Hello");
        }

        //as of now we got the username and tocken from the Authorization header of the request Body
            //Now we will check if that exist in security conext or not, if it already exist no need to validate the token, we
            //will only validate if it doesnt exist.
            if(userName!=null&& SecurityContextHolder.getContext().getAuthentication()==null)
            {
                System.out.println("Inside 2nd f");
                //we are getting the UserDetails Object from UseerDetailsService via ApplicationContext
                // adn validating with the token.
                UserDetails userDetails=context.getBean(UserDetailsService.class).loadUserByUsername(userName);
                if(jwtService.validateToken(token, userDetails)){
                    //If the token is validated we are setting the UsernamePasswordAuthTockenFilter so if it goes to the next
                    // filter it will pass it
                    // and simultaneusly we are adding the tocken into the securtiy Context Holder.
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            System.out.println("ouside 2nd f");
            //Passing the filter to the UserNamePassWordAuthentication
            filterChain.doFilter(request,response);
        }
}
