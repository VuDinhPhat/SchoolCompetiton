package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.model.entity.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    ResponseEntity<ResponseObj> getAllTeam();
    ResponseEntity<ResponseObj> getTeamById(int id);
    ResponseEntity<ResponseObj> getTeamByName(String name);
}
