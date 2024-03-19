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
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping("getList")
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return competitionService.getListCompetitions(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return competitionService.getCompetitionById(id);
    }
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createCompetition(@RequestBody CreateCompetitionRequest competitionRequest) {
        return competitionService.createCompetition(competitionRequest);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateCompetition(@RequestParam int id, @RequestBody UpdateCompetitionRequest updateCompetitionRequest) {
        return competitionService.updateCompetition(id, updateCompetitionRequest);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteCar(@RequestParam int id) {
        return competitionService.deleteCompetition(id);
    }
}
