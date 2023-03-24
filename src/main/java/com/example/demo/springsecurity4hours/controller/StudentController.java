package com.example.demo.springsecurity4hours.controller;

import com.example.demo.springsecurity4hours.entity.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private static final List<Student> STUDENT_LIST = Arrays.asList(
            new Student(1,"James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3,"Ana Smith")
    );

    @GetMapping("/{studentId}")
    public Student getStudent(@PathVariable("studentId") Integer studentId) {
        return  STUDENT_LIST.stream().filter(student -> studentId.equals(student.getStudentId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("student "+studentId +"does not exists"));
    }
}
