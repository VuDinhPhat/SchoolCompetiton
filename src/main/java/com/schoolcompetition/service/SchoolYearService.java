package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.SchoolYearRequest.CreateSchoolYearRequest;
import com.schoolcompetition.model.dto.request.SchoolYearRequest.UpdateSchoolYearRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolYearService {
    ResponseEntity<ResponseObj> getListSchoolsYear(int page, int size);
    ResponseEntity<ResponseObj> getSchoolYearById(int id);
    ResponseEntity<ResponseObj> getSchoolYearByYear(int year);
    ResponseEntity<ResponseObj> createSchoolYear(CreateSchoolYearRequest requestSchoolYear);
    ResponseEntity<ResponseObj> updateSchoolYear(int id, UpdateSchoolYearRequest requestSchoolYear);
    ResponseEntity<ResponseObj> deleteSchoolYear(int id);


}
