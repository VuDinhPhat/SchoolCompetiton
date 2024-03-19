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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return studentService.getListStudents(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return studentService.getStudentById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return studentService.getStudentByName(name);}
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createStudent(@RequestBody CreateStudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateStudent(@RequestParam int id, @RequestBody UpdateStudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteStudent(@RequestParam int id) {
        return studentService.deleteStudent(id);
    }
}
