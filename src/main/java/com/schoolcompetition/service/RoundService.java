package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.RoundRequest.CreateRoundRequest;
import com.schoolcompetition.model.dto.request.RoundRequest.UpdateRoundRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.model.entity.Round;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoundService {
    ResponseEntity<ResponseObj> getListRounds(int page, int size) ;

    ResponseEntity<ResponseObj> getRoundById(int id);
    ResponseEntity<ResponseObj> getRoundByName(String name);
    ResponseEntity<ResponseObj> createRound(CreateRoundRequest roundRequest);
    ResponseEntity<ResponseObj> updateRound(int id, UpdateRoundRequest roundRequest);
    ResponseEntity<ResponseObj> deleteRound(int id);

}
