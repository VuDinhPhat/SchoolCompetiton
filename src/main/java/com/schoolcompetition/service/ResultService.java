package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.model.entity.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultService {
    ResponseEntity<ResponseObj> getAllResult();

    ResponseEntity<ResponseObj> getResultById(int id);
}