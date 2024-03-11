package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.dto.ResponseObj;
import com.schoolcompetition.model.dto.SchoolYearResponse;
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
public class SchoolYearServiceImpl implements SchoolYearService {
    SchoolYearRepository schoolYearRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllSchoolYear() {
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        List<SchoolYearResponse> responses = new ArrayList<>();

        for (SchoolYear schoolYear : schoolYearList) {
            SchoolYearResponse syResponse = new SchoolYearResponse();
            syResponse.setId(schoolYear.getId());
            syResponse.setYear(schoolYear.getYear());
            responses.add(syResponse);
        }

        if (!responses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("School years loaded successfully")
                    .data(responses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No school years found")
                .build();
        return ResponseEntity.ok().body(responseObj);
    }
}
