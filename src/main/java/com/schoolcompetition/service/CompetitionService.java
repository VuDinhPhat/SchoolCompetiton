package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.CompetitionRequest.CreateCompetitionRequest;
import com.schoolcompetition.model.dto.request.CompetitionRequest.UpdateCompetitionRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Competition;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    ResponseEntity<ResponseObj> getListCompetitions(int page, int size);

    ResponseEntity<ResponseObj> getCompetitionById(int id);
    ResponseEntity<ResponseObj> getCompetitionByName(String name);
    ResponseEntity<ResponseObj> createCompetition(CreateCompetitionRequest competitionRequest);
    ResponseEntity<ResponseObj> updateCompetition(int id, UpdateCompetitionRequest updateCompetitionRequest);
    ResponseEntity<ResponseObj> deleteCompetition(int id);

}
