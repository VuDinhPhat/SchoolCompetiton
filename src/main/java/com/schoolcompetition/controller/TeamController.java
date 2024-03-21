package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.StudentRequest.CreateStudentRequest;
import com.schoolcompetition.model.dto.request.StudentRequest.UpdateStudentRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-management")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping(value = {"teams"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return teamService.getListTeams(page, size);
    }

    @GetMapping(value = {"team/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return teamService.getTeamById(id);
    }

    @GetMapping(value = {"team/name/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return teamService.getTeamByName(name);}

    @PostMapping("team")
    public ResponseEntity<ResponseObj> createTeam(@RequestBody CreateTeamRequest teamRequest) {
        return teamService.createTeam(teamRequest);
    }

    @PutMapping("team/{id}")
    public ResponseEntity<ResponseObj> updateTeam(@PathVariable int id, @RequestBody UpdateTeamRequest updateTeam) {
        return teamService.updateTeam(id, updateTeam);
    }

    @PutMapping("team-status/{id}")
    public ResponseEntity<ResponseObj> deleteTeam(@PathVariable int id) {
        return teamService.deleteTeam(id);
    }
    @GetMapping
    public int countTotalTeam(){return teamService.countTotalTeam();}
}
