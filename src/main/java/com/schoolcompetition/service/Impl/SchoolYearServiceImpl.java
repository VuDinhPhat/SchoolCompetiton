package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
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
import java.util.Map;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SchoolYearServiceImpl implements SchoolYearService  {
    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Override
    public ResponseEntity<ResponseObj> getListSchoolsYear(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<SchoolYear> schoolYearPage = schoolYearRepository.findAll(pageable);

            // Kiểm tra xem trang có dữ liệu không
            if (schoolYearPage.hasContent()) {
                List<SchoolYearResponse> schoolYearResponses = new ArrayList<>();

                for (SchoolYear schoolYear : schoolYearPage.getContent()) {
                    schoolYearResponses.add(SchoolYearMapper.toSchoolYearResponse(schoolYear));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all SchoolYears successfully")
                        .data(schoolYearResponses)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            } else {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("No data found on page " + page)
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message("Failed to load SchoolYears")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
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
            schoolYear.setStatus(requestSchoolYear.getStatus());

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

            if (requestSchoolYear.getYear() != 0) {
                schoolYear.setYear(requestSchoolYear.getYear());
            }
            if (requestSchoolYear.getStatus()!= null) {
                schoolYear.setStatus(requestSchoolYear.getStatus());
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

    @Override
    public ResponseEntity<ResponseObj> deleteSchoolYear(int id) {
        SchoolYear schoolYearToDelete = schoolYearRepository.getReferenceById(id);
        List<SchoolYear> schoolYearList = schoolYearRepository.findAll();

        for (SchoolYear schoolYear : schoolYearList) {
            if (schoolYear.equals(schoolYearToDelete)) {
                schoolYear.setStatus(Status.IN_ACTIVE);
                schoolYearRepository.save(schoolYear);

                SchoolYearResponse schoolYearResponse = SchoolYearMapper.toSchoolYearResponse(schoolYearToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("School year status changed to INACTIVE")
                        .data(schoolYearResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("School year not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }

}
