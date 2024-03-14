package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return teamService.getAllTeam();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return teamService.getTeamById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return teamService.getTeamByName(name);}
}
