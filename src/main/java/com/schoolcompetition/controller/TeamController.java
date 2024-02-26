package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Team>>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<Team> teams = teamService.getAllTeam();
        apiResponse.ok(teams);
        return ResponseEntity.ok(apiResponse);
    }
}
