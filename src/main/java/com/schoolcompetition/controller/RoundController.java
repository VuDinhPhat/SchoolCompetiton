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
@RequestMapping("/round")
public class RoundController {
    @Autowired
    RoundService roundService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return roundService.getAllRound();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return roundService.getRoundById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return roundService.getRoundByName(name);}
    @PostMapping("/create")
    public ResponseEntity<ResponseObj> createRound(@RequestBody CreateRoundRequest roundRequest) {
        return roundService.createRound(roundRequest);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObj> updateRound(@PathVariable int id, @RequestBody UpdateRoundRequest updateRoundRequest) {
        return roundService.updateRound(id, updateRoundRequest);
    }
}
