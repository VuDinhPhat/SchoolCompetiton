package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.dto.ResponseObj;
import com.schoolcompetition.model.dto.TeamResponse;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllTeam() {
        List<Team> teamList = teamRepository.findAll();
        List<TeamResponse> responses = new ArrayList<>();

        for (Team team : teamList) {
            TeamResponse teamResponse = new TeamResponse();
            teamResponse.setId(team.getId());
            teamResponse.setName(team.getName());
            responses.add(teamResponse);
        }

        if (!responses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Teams loaded successfully")
                    .data(responses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No teams found")
                .build();
        return ResponseEntity.ok().body(responseObj);
    }

//    @Override
//    public List<Team> getAllTeam() {
//        return teamRepository.findAll();
//    }
}
