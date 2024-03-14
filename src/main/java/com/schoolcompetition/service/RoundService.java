package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.model.entity.Round;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoundService {
    ResponseEntity<ResponseObj> getAllRound();

    ResponseEntity<ResponseObj> getRoundById(int id);
}
