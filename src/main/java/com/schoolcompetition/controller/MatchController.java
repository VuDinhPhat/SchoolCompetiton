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
@RequestMapping("/api/match-management")
public class MatchController {
    @Autowired
    MatchService matchService;

    @GetMapping(value = {"matches"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return matchService.getListMatches(page, size);
    }


    @GetMapping(value = {"match/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return matchService.getById(id);
    }

    @GetMapping(value = {"match/name/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return matchService.getMatchByName(name);}

    @PostMapping("match")
    public ResponseEntity<ResponseObj> createMatch(@RequestBody CreateMatchRequest matchRequest) {
        return matchService.createMatch(matchRequest);
    }

    @PutMapping("match/{id}")
    public ResponseEntity<ResponseObj> updateMatch(@PathVariable int id, @RequestBody UpdateMatchRequest matchRequest) {
        return matchService.updateMatch(id, matchRequest);
    }

    @PutMapping("match-status/{id}")
    public ResponseEntity<ResponseObj> deleteMatch(@PathVariable int id) {
        return matchService.deleteMatch(id);
    }
    @GetMapping("total")
    public int countTotalMatch(){return matchService.countTotalMatch();}
}
