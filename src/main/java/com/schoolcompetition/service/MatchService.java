package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Match;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MatchService {
    ResponseEntity<ResponseObj> getAll();

    ResponseEntity<ResponseObj> getById(int id);
}
