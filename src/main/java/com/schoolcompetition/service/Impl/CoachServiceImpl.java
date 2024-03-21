package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CoachMapper;
import com.schoolcompetition.model.dto.request.CoachRequest.CreateCoachRequest;
import com.schoolcompetition.model.dto.request.CoachRequest.UpdateCoachRequest;
import com.schoolcompetition.model.dto.response.CoachResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.enums.Status;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<ResponseObj> getListCoaches(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Coach> coachPage = coachRepository.findAll(pageable);

            if (coachPage.hasContent()) {
                List<CoachResponse> coachResponses = new ArrayList<>();

                for (Coach coach : coachPage.getContent()) {
                    coachResponses.add(CoachMapper.toCoachResponse(coach));
                }

                Map<String, Object> response = new HashMap<>();
                response.put("Coach", coachResponses);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Coach successfully")
                        .data(response)
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
                    .message("Failed to load Coach data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getCoachById(int id) {
        Coach coach = coachRepository.getReferenceById(id);
        List<Coach> coachList = coachRepository.findAll();

        for (Coach c : coachList) {
            if (c.equals(coach) && c.getStatus().equals(Status.ACTIVE)) {
                CoachResponse coachResponse = CoachMapper.toCoachResponse(coach);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Coach founded")
                        .data(coachResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
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
            if (coach.getName().toLowerCase().contains(name.toLowerCase()) && coach.getStatus().equals(Status.ACTIVE)) {

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
            coach.setStatus(coachRequest.getStatus());

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
            if (coachRequest.getSex() != null) {
                coach.setSex(coachRequest.getSex());
            }
            if (coachRequest.getStatus() != null) {
                coach.setStatus(coachRequest.getStatus());
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


    @Override
    public ResponseEntity<ResponseObj> deleteCoach(int id) {
        Coach coachToDelete = coachRepository.getReferenceById(id);
        List<Coach> coachList = coachRepository.findAll();

        for (Coach coach : coachList) {
            if (coach.equals(coachToDelete)) {
                coach.setStatus(Status.IN_ACTIVE); // Chuyển trạng thái thành INACTIVE
                coachRepository.save(coach); // Lưu lại thay đổi vào cơ sở dữ liệu

                CoachResponse coachResponse = CoachMapper.toCoachResponse(coachToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Coach status changed to INACTIVE")
                        .data(coachResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        // Nếu không tìm thấy huấn luyện viên
        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Coach not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }

    @Override
    public int countTotalCoach() {
        List<Coach> coachList = coachRepository.findAll();
        return coachList.size();
    }


}
