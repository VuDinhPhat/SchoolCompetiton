package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.CoachRequest.CreateCoachRequest;
import com.schoolcompetition.model.dto.request.CoachRequest.UpdateCoachRequest;
import com.schoolcompetition.model.dto.request.StudentRequest.CreateStudentRequest;
import com.schoolcompetition.model.dto.request.StudentRequest.UpdateStudentRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    ResponseEntity<ResponseObj> getListStudents(int page, int size);
    ResponseEntity<ResponseObj> getStudentById(int id);
    ResponseEntity<ResponseObj> getStudentByName(String name);
    ResponseEntity<ResponseObj> createStudent(CreateStudentRequest coachRequest);
    ResponseEntity<ResponseObj> updateStudent(int id, UpdateStudentRequest coachRequest);
    ResponseEntity<ResponseObj> deleteStudent(int id);
    int countTotalStudent();

}
