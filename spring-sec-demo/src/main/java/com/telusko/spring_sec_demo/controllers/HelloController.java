package com.telusko.spring_sec_demo.controllers;

import com.telusko.spring_sec_demo.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/Hello")
    public String hello(HttpServletRequest request)
    {
        return "hello world "+(String) request.getSession().getId();
    }

    @RestController
    public static class StudentController {
        List<Student> studentList=new ArrayList<>(List.of(new Student(1,"Pranay",22),new Student(2,"Tarak",23)));

        @GetMapping("/allStudents")
        public String getAllStudents()
        {
            return studentList.toString();
        }
        @GetMapping("csrf-token")
        public CsrfToken getCsrfToken(HttpServletRequest request)
        {
            return (CsrfToken) request.getAttribute("_csrf");
        }
        @PostMapping("/updateStudent")
        public void addStudent(@RequestBody Student student)
        {
            studentList.add(student);
        }
    }
}
