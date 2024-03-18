package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.SchoolRequest.CreateSchoolRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.UpdateSchoolRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.School;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService {
    ResponseEntity<ResponseObj> getListSchools(int page, int size);
    ResponseEntity<ResponseObj> getSchoolById(int id);
    ResponseEntity<ResponseObj> getSchoolByName(String name);
    ResponseEntity<ResponseObj> createSchool(CreateSchoolRequest requestSchool);
    ResponseEntity<ResponseObj> updateSchool(int id, UpdateSchoolRequest requestSchool);
    ResponseEntity<ResponseObj> deleteSchool(int id);

}
