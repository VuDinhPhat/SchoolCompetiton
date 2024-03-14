package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.BracketRequest.CreateBracketRequest;
import com.schoolcompetition.model.dto.request.BracketRequest.UpdateBracketRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BracketService {

    ResponseEntity<ResponseObj> getAllBracket();

    ResponseEntity<ResponseObj> getBracketById(int id);

    ResponseEntity<ResponseObj> getByName(String name);
    ResponseEntity<ResponseObj> createBracket(CreateBracketRequest bracketRequest);
    ResponseEntity<ResponseObj> updateBracket(int id, UpdateBracketRequest bracketRequest);
}
