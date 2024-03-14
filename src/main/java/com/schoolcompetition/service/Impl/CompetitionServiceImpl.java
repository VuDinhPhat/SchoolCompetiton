package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CompetitionMapper;
import com.schoolcompetition.model.dto.response.CompetitionResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.repository.CompetitionRepository;
import com.schoolcompetition.service.CompetitionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllCompetition(){
        List<Competition> competitionList = competitionRepository.findAll();
        List<CompetitionResponse> competitionResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Competition competition : competitionList) {
            competitionResponses.add(CompetitionMapper.toCompetitionResponse(competition));
        }
        response.put("Competition", competitionResponses);

        if (!competitionResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Load all Competition successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Competition failed")
                .data(null)
                .build();
        return ResponseEntity.ok().body(responseObj);

    }

    @Override
    public ResponseEntity<ResponseObj> getCompetitionById(int id) {
        Competition competition = competitionRepository.getReferenceById(id);

        if(competition != null) {
            CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(competition);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket founded")
                    .data(competitionResponse)
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
