package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.repository.SchoolYearRepository;
import com.schoolcompetition.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolYearServiceImpl implements SchoolYearService {
    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Override
    public List<SchoolYear> getAll(){
        return schoolYearRepository.findAll();
    }
}
