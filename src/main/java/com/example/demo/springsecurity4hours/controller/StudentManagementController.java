package com.example.demo.springsecurity4hours.controller;

import com.example.demo.springsecurity4hours.entity.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

    private static final List<Student> STUDENT_LIST = Arrays.asList(
            new Student(1,"James Bond"),
            new Student(2, "Maria Jones"),
            new Student(3,"Ana Smith")
    );

    @GetMapping
    public List<Student> getStudentList () {
        return STUDENT_LIST;
    }
    @PostMapping
    public void registerNewStudent (@RequestBody  Student student) {
        System.out.println(student);
    }
    @DeleteMapping("{studentId}")
    public void deleteStudent (@PathVariable Integer studentId) {
        System.out.println(studentId);
    }
    @PutMapping("{studentId}")
    public void updateStudent (@RequestBody Student student , @PathVariable Integer studentId) {
        System.out.println(String.format("%s %s " ,student, studentId));
    }



}
