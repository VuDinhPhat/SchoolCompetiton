package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.StudentRequest.CreateStudentRequest;
import com.schoolcompetition.model.dto.request.StudentRequest.UpdateStudentRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-management")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"students"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return studentService.getListStudents(page, size);
    }

    @GetMapping(value = {"student/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @GetMapping(value = {"student/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return studentService.getStudentByName(name);}

    @PostMapping("student")
    public ResponseEntity<ResponseObj> createStudent(@RequestBody CreateStudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @PutMapping("student/{id}")
    public ResponseEntity<ResponseObj> updateStudent(@PathVariable int id, @RequestBody UpdateStudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @PutMapping("student/{id}")
    public ResponseEntity<ResponseObj> deleteStudent(@PathVariable int id) {
        return studentService.deleteStudent(id);
    }
}
