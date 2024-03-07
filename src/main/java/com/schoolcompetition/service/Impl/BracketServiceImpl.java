package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.dto.BracketResponse;
import com.schoolcompetition.model.dto.ResponseObj;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.repository.BracketRepository;
import com.schoolcompetition.service.BracketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BracketServiceImpl implements BracketService {
    @Autowired
    BracketRepository bracketRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllBracket() {
        List<Bracket> bracketList = bracketRepository.findAll();
        List<BracketResponse> responses = new ArrayList<>();

        for (Bracket bracket : bracketList) {
            BracketResponse br = new BracketResponse();
            br.id = bracket.getId();
            br.name = bracket.getName();
            responses.add(br);
        }

        if (!responses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load successfully")
                    .data(responses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .data(responses)
                .build();
        return ResponseEntity.ok().body(responseObj);
    }


}
