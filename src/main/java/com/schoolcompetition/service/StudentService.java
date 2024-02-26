package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StudentService {
    List<Student> getAll();
}
