package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.StudentResponse;
import com.schoolcompetition.model.entity.Student;

public class StudentMapper {
    public static StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .dob(student.getDob())
                .sex(student.getSex())
                .school(student.getSchool())
                .status(student.getStatus())
                .build();
    }
}
