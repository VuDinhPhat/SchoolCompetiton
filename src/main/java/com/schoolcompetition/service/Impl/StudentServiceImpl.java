package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolYearMapper;
import com.schoolcompetition.mapper.StudentMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolYearResponse;
import com.schoolcompetition.model.dto.response.StudentResponse;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.repository.StudentRepository;
import com.schoolcompetition.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllStudent() {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentsResponses = new ArrayList<>();

        for (Student students : studentList) {
            studentsResponses.add(StudentMapper.toStudentResponse(students));
        }

        if (!studentsResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Student successfully")
                    .data(studentsResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Schools failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getStudentById(int id) {
        Student student = studentRepository.findById(id).orElse(null);

        if (student != null) {
            StudentResponse studentResponse = StudentMapper.toStudentResponse(student);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Student founded")
                    .data(studentResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }
}
