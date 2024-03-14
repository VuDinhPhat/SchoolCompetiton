package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.MatchMapper;
import com.schoolcompetition.model.dto.response.MatchResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.repository.MatchRepository;
import com.schoolcompetition.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Override
    public ResponseEntity<ResponseObj> getAll() {
        List<Match> matchList = matchRepository.findAll();
        List<MatchResponse> matchResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Match match : matchList) {
            matchResponses.add(MatchMapper.toMatchResponse(match));
        }
        response.put("Match", matchResponses);

        if (!matchResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Match successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Match failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getById(int id) {
        Match match = matchRepository.getReferenceById(id);

        if (match != null) {
            MatchResponse matchResponse = MatchMapper.toMatchResponse(match);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Match founded")
                    .data(matchResponse)
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
