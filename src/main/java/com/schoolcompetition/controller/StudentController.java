package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.service.BracketService;
import com.schoolcompetition.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Student>>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<Student> students = studentService.getAll();
        apiResponse.ok(students);
        return ResponseEntity.ok(apiResponse);
    }
}
