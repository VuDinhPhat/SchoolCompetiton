package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CoachMapper;
import com.schoolcompetition.model.dto.response.CoachResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.repository.CoachRepository;
import com.schoolcompetition.service.CoachService;
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

}
