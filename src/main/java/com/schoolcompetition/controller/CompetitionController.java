package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/competition")
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Competition>>> getAllClass() {
        ApiResponse apiResponse = new ApiResponse();
        List<Competition> competitions = competitionService.getAllCompetition();
        apiResponse.ok(competitions);
        return ResponseEntity.ok(apiResponse);
    }
}
