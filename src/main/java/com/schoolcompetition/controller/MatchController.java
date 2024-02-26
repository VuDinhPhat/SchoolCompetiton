package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    MatchService matchService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Match>>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<Match> matches = matchService.getAllMatch();
        apiResponse.ok(matches);
        return ResponseEntity.ok(apiResponse);
    }
}
