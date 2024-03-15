package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolYearMapper;
import com.schoolcompetition.mapper.StudentMapper;
import com.schoolcompetition.model.dto.request.StudentRequest.CreateStudentRequest;
import com.schoolcompetition.model.dto.request.StudentRequest.UpdateStudentRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolYearResponse;
import com.schoolcompetition.model.dto.response.StudentResponse;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.repository.StudentRepository;
import com.schoolcompetition.service.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SchoolRepository schoolRepository;

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

    @Override
    public ResponseEntity<ResponseObj> getStudentByName(String name) {
        List<Student> studentList = studentRepository.findAll();
        List<StudentResponse> studentResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Student student : studentList) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                studentResponses.add(StudentMapper.toStudentResponse(student));
            }
        }
        response.put("Students", studentResponses);

        if (!studentResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + studentResponses.size() + " record(s) matching")
                    .data(response)
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

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createStudent(CreateStudentRequest studentRequest) {
        try {
            School school = schoolRepository.findById(studentRequest.getSchoolId()).orElse(null);
            if (school == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Student student = new Student();
            student.setName(studentRequest.getName());
            student.setDob(studentRequest.getDob());
            student.setSex(studentRequest.getSex());
            student.setSchool(school);

            Student savedStudent = studentRepository.save(student);

            StudentResponse studentResponse = StudentMapper.toStudentResponse(savedStudent);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Student created successfully")
                    .data(studentResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create student")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateStudent(int id, UpdateStudentRequest studentRequest) {
        try {
            Student student = studentRepository.findById(id).orElse(null);
            if (student == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Student not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (studentRequest.getName() != null) {
                student.setName(studentRequest.getName());
            }
            if (studentRequest.getDob() != null) {
                student.setDob(studentRequest.getDob());
            }
            if (studentRequest.getSex() != null) {
                student.setSex(studentRequest.getSex());
            }
            if (studentRequest.getSchoolId() != 0) {
                School school = schoolRepository.findById(studentRequest.getSchoolId()).orElse(null);
                if (school == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("School not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                student.setSchool(school);
            }

            Student updatedStudent = studentRepository.save(student);

            StudentResponse studentResponse = StudentMapper.toStudentResponse(updatedStudent);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Student updated successfully")
                    .data(studentResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update student")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

}
