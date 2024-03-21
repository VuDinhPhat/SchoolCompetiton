package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.CompetitionRequest.CreateCompetitionRequest;
import com.schoolcompetition.model.dto.request.CompetitionRequest.UpdateCompetitionRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competition-management")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping("competitions")
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return competitionService.getListCompetitions(page, size);
    }

    @GetMapping(value = {"competition/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return competitionService.getCompetitionById(id);
    }

    @GetMapping("competition/{name}")
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) {
        return competitionService.getCompetitionByName(name);
    }

    @PostMapping("competition")
    public ResponseEntity<ResponseObj> createCompetition(@RequestBody CreateCompetitionRequest competitionRequest) {
        return competitionService.createCompetition(competitionRequest);
    }
    @PutMapping("competition/{id}")
    public ResponseEntity<ResponseObj> updateCompetition(@PathVariable int id, @RequestBody UpdateCompetitionRequest updateCompetitionRequest) {
        return competitionService.updateCompetition(id, updateCompetitionRequest);
    }
    @PutMapping("competition-status/{id}")
    public ResponseEntity<ResponseObj> deleteCar(@PathVariable int id) {
        return competitionService.deleteCompetition(id);
    }
}
