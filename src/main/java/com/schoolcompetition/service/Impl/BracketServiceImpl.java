package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.BrackerMapper;
import com.schoolcompetition.model.dto.response.BracketResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.repository.BracketRepository;
import com.schoolcompetition.service.BracketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BracketServiceImpl implements BracketService {
    @Autowired
    BracketRepository bracketRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllBracket() {
        List<Bracket> bracketList = bracketRepository.findAll();
        List<BracketResponse> bracketResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Bracket bracket : bracketList) {
            bracketResponses.add(BrackerMapper.toBracketResponse(bracket));
        }
        response.put("Bracket", bracketResponses);

        if (!bracketResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Bracket successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Bracket failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getBracketById(int id) {
        Bracket bracket = bracketRepository.getReferenceById(id);

        if (bracket != null) {
            BracketResponse bracketResponse = BrackerMapper.toBracketResponse(bracket);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket founded")
                    .data(bracketResponse)
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
    public ResponseEntity<ResponseObj> getByName(String name) {
        List<Bracket> bracketList = bracketRepository.findAll();
        List<BracketResponse> bracketResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Bracket bracket : bracketList) {
            if (bracket.getName().toLowerCase().contains(name)) {
                bracketResponses.add(BrackerMapper.toBracketResponse(bracket));
            }
        }
        response.put("Bracket", bracketResponses);

        if (!bracketResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + bracketResponses.size() + " record matching")
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
