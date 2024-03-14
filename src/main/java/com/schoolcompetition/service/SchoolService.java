package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.School;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService {
    ResponseEntity<ResponseObj> getAllSchools();
    ResponseEntity<ResponseObj> getSchoolById(int id);
    ResponseEntity<ResponseObj> getSchoolByName(String name);
}
