package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CoachMapper;
import com.schoolcompetition.model.dto.request.CoachRequest.CreateCoachRequest;
import com.schoolcompetition.model.dto.request.CoachRequest.UpdateCoachRequest;
import com.schoolcompetition.model.dto.response.CoachResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.repository.CoachRepository;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.service.CoachService;
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
public class CoachServiceImpl implements CoachService {
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    SchoolRepository schoolRepository;
    @Override
    public ResponseEntity<ResponseObj> getAllCoach() {
        List<Coach> coachList = coachRepository.findAll();
        List<CoachResponse> coachResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Coach coach : coachList) {
            coachResponses.add(CoachMapper.toCoachResponse(coach));
        }
        response.put("Bracket", coachResponses);

        if (!coachResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Coach successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Bracket failed")
                .data(null)
                .build();
        return ResponseEntity.ok().body(responseObj);

    }

    @Override
    public ResponseEntity<ResponseObj> getCoachById(int id) {
        Coach coach = coachRepository.getReferenceById(id);

        if (coach != null) {
            CoachResponse coachResponse = CoachMapper.toCoachResponse(coach);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Coach founded")
                    .data(coachResponse)
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
    public ResponseEntity<ResponseObj> getCoachByName(String name) {
        List<Coach> coachList = coachRepository.findAll();
        List<CoachResponse> coachResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Coach coach : coachList) {
            if (coach.getName().toLowerCase().contains(name.toLowerCase())) {

                coachResponses.add(CoachMapper.toCoachResponse(coach));
            }
        }
        response.put("Coaches", coachResponses);

        if (!coachResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + coachResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createCoach(CreateCoachRequest coachRequest) {
        try {
            // Kiểm tra xem schoolId trong request có tồn tại không
            School school = schoolRepository.findById(coachRequest.getSchoolId()).orElse(null);
            if (school == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Coach coach = new Coach();
            coach.setName(coachRequest.getName());
            coach.setDob(coachRequest.getDob());
            coach.setSex(coachRequest.getSex());
            coach.setSchool(school);

            Coach savedCoach = coachRepository.save(coach);

            CoachResponse coachResponse = CoachMapper.toCoachResponse(savedCoach);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Coach created successfully")
                    .data(coachResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create coach")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateCoach(int id, UpdateCoachRequest coachRequest) {
        try {
            Coach coach = coachRepository.findById(id).orElse(null);
            if (coach == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Coach not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (coachRequest.getName() != null) {
                coach.setName(coachRequest.getName());
            }
            if (coachRequest.getDob() != null) {
                coach.setDob(coachRequest.getDob());
            }
            if (coachRequest.getSex() != 0) {
                coach.setSex(coachRequest.getSex());
            }
            if (coachRequest.getSchoolId() != 0) {
                School school = schoolRepository.findById(coachRequest.getSchoolId()).orElse(null);
                if (school == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("School not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                coach.setSchool(school);
            }

            Coach updatedCoach = coachRepository.save(coach);

            CoachResponse coachResponse = CoachMapper.toCoachResponse(updatedCoach);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Coach updated successfully")
                    .data(coachResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update coach")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }


}
