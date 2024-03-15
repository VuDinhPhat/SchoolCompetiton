package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolMapper;
import com.schoolcompetition.mapper.SchoolYearMapper;
import com.schoolcompetition.model.dto.request.SchoolYearRequest.CreateSchoolYearRequest;
import com.schoolcompetition.model.dto.request.SchoolYearRequest.UpdateSchoolYearRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolResponse;
import com.schoolcompetition.model.dto.response.SchoolYearResponse;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.repository.SchoolYearRepository;
import com.schoolcompetition.service.SchoolYearService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResponseEntity<ResponseObj> getSchoolYearByYear(int year) {
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();
        List<SchoolYearResponse> schoolYearResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (SchoolYear schoolYear : schoolYearList) {
            if (schoolYear.getYear() == year) {
                schoolYearResponses.add(SchoolYearMapper.toSchoolYearResponse(schoolYear));
            }
        }

        response.put("SchoolYears", schoolYearResponses);

        if (!schoolYearResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + schoolYearResponses.size() + " record(s) matching")
                    .data(response)
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


    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createSchoolYear(CreateSchoolYearRequest requestSchoolYear) {
        try {
            ResponseEntity<ResponseObj> checkResponse = getSchoolYearByYear(requestSchoolYear.getYear());
            if (checkResponse.getStatusCode() == HttpStatus.OK) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.BAD_REQUEST))
                        .message("School year already exists")
                        .data(null)
                        .build();
                return ResponseEntity.badRequest().body(responseObj);
            }

            SchoolYear schoolYear = new SchoolYear();
            schoolYear.setYear(requestSchoolYear.getYear());

            SchoolYear savedSchoolYear = schoolYearRepository.save(schoolYear);

            SchoolYearResponse schoolYearResponse = SchoolYearMapper.toSchoolYearResponse(savedSchoolYear);

            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("School year created successfully")
                    .data(schoolYearResponse)
                    .build();

            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create school year")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateSchoolYear(int id, UpdateSchoolYearRequest requestSchoolYear) {
        try {
            SchoolYear schoolYear = schoolYearRepository.findById(id).orElse(null);
            if (schoolYear == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School year not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Kiểm tra xem requestSchoolYear có chứa dữ liệu mới cho trường year không
            if (requestSchoolYear.getYear() != 0) {
                schoolYear.setYear(requestSchoolYear.getYear());
            }

            SchoolYear updatedSchoolYear = schoolYearRepository.save(schoolYear);

            SchoolYearResponse schoolYearResponse = SchoolYearMapper.toSchoolYearResponse(updatedSchoolYear);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("School year updated successfully")
                    .data(schoolYearResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update school year")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }
}
