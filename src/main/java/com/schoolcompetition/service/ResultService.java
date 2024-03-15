package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.ResultRequest.CreateResultRequest;
import com.schoolcompetition.model.dto.request.ResultRequest.UpdateResultRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
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
    ResponseEntity<ResponseObj> createResult(CreateResultRequest resultRequest);
    ResponseEntity<ResponseObj> updateResult(int id, UpdateResultRequest resultRequest);
}