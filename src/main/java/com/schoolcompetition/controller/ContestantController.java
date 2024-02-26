package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.service.BracketService;
import com.schoolcompetition.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/contestant")
public class ContestantController {
    @Autowired
    ContestantService contestantService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Contestant>>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<Contestant> contestants = contestantService.getAll();
        apiResponse.ok(contestants);
        return ResponseEntity.ok(apiResponse);
    }
}
