package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.MatchRequest.CreateMatchRequest;
import com.schoolcompetition.model.dto.request.MatchRequest.UpdateMatchRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Match;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MatchService {
    ResponseEntity<ResponseObj> getAll();

    ResponseEntity<ResponseObj> getById(int id);
    ResponseEntity<ResponseObj> getMatchByName(String name);
    ResponseEntity<ResponseObj> createMatch(CreateMatchRequest createMatchRequest);
    ResponseEntity<ResponseObj> updateMatch(int id, UpdateMatchRequest updateMatchRequest);
}
