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
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamService teamService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return teamService.getListTeams(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return teamService.getTeamById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return teamService.getTeamByName(name);}
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createTeam(@RequestBody CreateTeamRequest teamRequest) {
        return teamService.createTeam(teamRequest);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateTeam(@PathVariable int id, @RequestBody UpdateTeamRequest updateTeam) {
        return teamService.updateTeam(id, updateTeam);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteTeam(@PathVariable int id) {
        return teamService.deleteTeam(id);
    }
}
