package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    ResponseEntity<ResponseObj> getAllStudent();
    ResponseEntity<ResponseObj> getStudentById(int id);
    ResponseEntity<ResponseObj> getStudentByName(String name);
}
