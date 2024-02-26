package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService{
    @Autowired
    SchoolRepository schoolRepository;
    @Override
    public List<School> getAll() {
        return schoolRepository.findAll();
    }
}
