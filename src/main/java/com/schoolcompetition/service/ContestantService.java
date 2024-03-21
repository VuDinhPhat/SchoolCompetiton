package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.ContestantRequest.CreateContestantRequest;
import com.schoolcompetition.model.dto.request.ContestantRequest.UpdateContestantRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Contestant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContestantService {
    ResponseEntity<ResponseObj> getListContestants(int page, int size);

    ResponseEntity<ResponseObj> getById(int id);
    ResponseEntity<ResponseObj> createContestant(CreateContestantRequest contestantRequest);
    ResponseEntity<ResponseObj> updateContestant(int id, UpdateContestantRequest contestantRequest);
    ResponseEntity<ResponseObj> deleteContestant(int id);

    int countTotalContestant();

}
