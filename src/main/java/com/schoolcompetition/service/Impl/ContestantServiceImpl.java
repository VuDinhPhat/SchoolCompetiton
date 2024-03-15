package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.BrackerMapper;
import com.schoolcompetition.mapper.ContestantMapper;
import com.schoolcompetition.model.dto.response.BracketResponse;
import com.schoolcompetition.model.dto.response.ContestantResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.repository.ContestantRepository;
import com.schoolcompetition.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestantServiceImpl implements ContestantService {
    @Autowired
    ContestantRepository contestantRepository;
    @Override
    public ResponseEntity<ResponseObj> getAll() {
        List<Contestant> contestantList = contestantRepository.findAll();
        List<ContestantResponse> contestantResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Contestant contestant : contestantList) {
            contestantResponses.add(ContestantMapper.toContestantResponse(contestant));
        }
        response.put("Contestant", contestantResponses);

        if (!contestantResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Contestant successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Contestant failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);

    }

    @Override
    public ResponseEntity<ResponseObj> getById(int id) {
        Contestant contestant = contestantRepository.getReferenceById(id);
        List<Contestant> contestantList = contestantRepository.findAll();

        for (Contestant c : contestantList) {
            if (c.equals(contestant)) {
                ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(contestant);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Bracket founded")
                        .data(contestantResponse)
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
}
