package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.SchoolMapper;
import com.schoolcompetition.model.dto.request.SchoolRequest.CreateSchoolRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.UpdateSchoolRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ResponseEntity<ResponseObj> getSchoolByName(String name) {
        List<School> schoolList = schoolRepository.findAll();
        List<SchoolResponse> schoolResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (School school : schoolList) {
            if (school.getName().toLowerCase().contains(name.toLowerCase())) {
                schoolResponses.add(SchoolMapper.toSchoolResponse(school));
            }
        }
        response.put("Schools", schoolResponses);

        if (!schoolResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + schoolResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createSchool(CreateSchoolRequest requestSchool) {
        try {
            School newSchool = new School();
            newSchool.setName(requestSchool.getName());
            newSchool.setAddress(requestSchool.getAddress());

            School savedSchool = schoolRepository.save(newSchool);

            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(savedSchool);

            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("School created successfully")
                    .data(schoolResponse)
                    .build();

            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create school")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> updateSchool(int id, UpdateSchoolRequest requestSchool) {
        try {
            School school = schoolRepository.findById(id).orElse(null);
            if (school == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (requestSchool.getName() != null && !requestSchool.getName().isEmpty()) {
                school.setName(requestSchool.getName());
            }

            if (requestSchool.getAddress() != null && !requestSchool.getAddress().isEmpty()) {
                school.setAddress(requestSchool.getAddress());
            }

            School updatedSchool = schoolRepository.save(school);

            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(updatedSchool);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("School updated successfully")
                    .data(schoolResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update school")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }
}
