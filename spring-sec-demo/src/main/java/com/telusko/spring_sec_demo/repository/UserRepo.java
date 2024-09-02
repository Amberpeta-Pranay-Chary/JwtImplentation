package com.telusko.spring_sec_demo.repository;

import com.telusko.spring_sec_demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM users WHERE username = :username", nativeQuery = true)
    public Optional<User> findByUserName(String username);
}
