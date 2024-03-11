package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.ResponseObj;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolYearService {
//    List<SchoolYear> getAll();
    ResponseEntity<ResponseObj> getAllSchoolYear();
}
