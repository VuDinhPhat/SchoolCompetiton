package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.MatchRequest.CreateMatchRequest;
import com.schoolcompetition.model.dto.request.MatchRequest.UpdateMatchRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    MatchService matchService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return matchService.getAll();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return matchService.getById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return matchService.getMatchByName(name);}
    @PostMapping("/create")
    public ResponseEntity<ResponseObj> createMatch(@RequestBody CreateMatchRequest matchRequest) {
        return matchService.createMatch(matchRequest);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObj> updateMatch(@PathVariable int id, @RequestBody UpdateMatchRequest matchRequest) {
        return matchService.updateMatch(id, matchRequest);
    }
}
