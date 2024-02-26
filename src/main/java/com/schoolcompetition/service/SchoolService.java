package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.School;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService {
    List<School> getAll();
}
