package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.repository.StudentRepository;
import com.schoolcompetition.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Override
    public List<Student> getAll(){
        return studentRepository.findAll();
    }
}
