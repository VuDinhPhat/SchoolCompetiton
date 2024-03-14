package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.ResultMapper;
import com.schoolcompetition.mapper.RoundMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.ResultResponse;
import com.schoolcompetition.model.dto.response.RoundResponse;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.repository.RoundRepository;
import com.schoolcompetition.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoundServiceImpl implements RoundService {
    @Autowired
    RoundRepository roundRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllRound() {
        List<Round> roundList = roundRepository.findAll();
        List<RoundResponse> roundResponses = new ArrayList<>();

        for (Round round : roundList) {
            roundResponses.add(RoundMapper.toRoundResponse(round));
        }

        if (!roundResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Round successfully")
                    .data(roundResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Round failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getRoundById(int id) {
        Round round = roundRepository.findById(id).orElse(null);

        if (round != null) {
            RoundResponse roundResponse = RoundMapper.toRoundResponse(round);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Result founded")
                    .data(roundResponse)
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
