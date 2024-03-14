package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolResponse;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    SchoolRepository schoolRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllSchools() {
        List<School> schoolList = schoolRepository.findAll();
        List<SchoolResponse> schoolResponses = new ArrayList<>();

        for (School school : schoolList) {
            schoolResponses.add(SchoolMapper.toSchoolResponse(school));
        }

        if (!schoolResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Schools successfully")
                    .data(schoolResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Schools failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getSchoolById(int id) {
        School school = schoolRepository.findById(id).orElse(null);

        if (school != null) {
            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(school);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("School founded")
                    .data(schoolResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }
}
