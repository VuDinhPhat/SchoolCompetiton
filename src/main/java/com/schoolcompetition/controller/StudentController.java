package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return studentService.getAllStudent();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return studentService.getStudentById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return studentService.getStudentByName(name);}
}
