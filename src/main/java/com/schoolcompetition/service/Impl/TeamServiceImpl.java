package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.StudentMapper;
import com.schoolcompetition.mapper.TeamMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.StudentResponse;
import com.schoolcompetition.model.dto.response.TeamResponse;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRepository teamRepository;


    @Override
    public ResponseEntity<ResponseObj> getAllTeam() {
        List<Team> teamList = teamRepository.findAll();
        List<TeamResponse> teamResponses = new ArrayList<>();

        for (Team team : teamList) {
            teamResponses.add(TeamMapper.toTeamResponse(team));
        }

        if (!teamResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Team successfully")
                    .data(teamResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Team failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getTeamById(int id) {
        Team team = teamRepository.findById(id).orElse(null);

        if (team != null) {
            TeamResponse teamResponse = TeamMapper.toTeamResponse(team);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Team founded")
                    .data(teamResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getTeamByName(String name) {
        List<Team> teamList = teamRepository.findAll();
        List<TeamResponse> teamResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Team team : teamList) {
            if (team.getName().toLowerCase().contains(name.toLowerCase())) {
                teamResponses.add(TeamMapper.toTeamResponse(team));
            }
        }
        response.put("Teams", teamResponses);

        if (!teamResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + teamResponses.size() + " record(s) matching")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

}
