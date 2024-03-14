package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolMapper;
import com.schoolcompetition.mapper.SchoolYearMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolResponse;
import com.schoolcompetition.model.dto.response.SchoolYearResponse;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.repository.SchoolYearRepository;
import com.schoolcompetition.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolYearServiceImpl implements SchoolYearService  {
    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllSchoolsYear() {
        List<SchoolYear> schoolYearsList = schoolYearRepository.findAll();
        List<SchoolYearResponse> schoolYearResponses = new ArrayList<>();

        for (SchoolYear schoolYear : schoolYearsList) {
            schoolYearResponses.add(SchoolYearMapper.toSchoolYearResponse(schoolYear));
        }

        if (!schoolYearResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Schools successfully")
                    .data(schoolYearResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all SchoolsYear failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getSchoolYearById(int id) {
        SchoolYear schoolYear = schoolYearRepository.findById(id).orElse(null);

        if (schoolYear != null) {
            SchoolYearResponse schoolYearResponse = SchoolYearMapper.toSchoolYearResponse(schoolYear);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("SchoolYear founded")
                    .data(schoolYearResponse)
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
