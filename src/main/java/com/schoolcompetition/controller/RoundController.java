package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.RoundRequest.CreateRoundRequest;
import com.schoolcompetition.model.dto.request.RoundRequest.UpdateRoundRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/round-management")
public class RoundController {
    @Autowired
    RoundService roundService;

    @GetMapping(value = {"rounds"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return roundService.getListRounds(page, size);
    }

    @GetMapping(value = {"round/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return roundService.getRoundById(id);
    }
    @GetMapping(value = {"round/name/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return roundService.getRoundByName(name);}
    @PostMapping("round")
    public ResponseEntity<ResponseObj> createRound(@RequestBody CreateRoundRequest roundRequest) {
        return roundService.createRound(roundRequest);
    }
    @PutMapping("round/{id}")
    public ResponseEntity<ResponseObj> updateRound(@PathVariable int id, @RequestBody UpdateRoundRequest updateRoundRequest) {
        return roundService.updateRound(id, updateRoundRequest);
    }
    @PutMapping("round-status/{id}")
    public ResponseEntity<ResponseObj> deleteRound(@PathVariable int id) {
        return roundService.deleteRound(id);
    }
    @GetMapping("total")
    public int countTotalRound(){return roundService.countTotalRound();}
}
