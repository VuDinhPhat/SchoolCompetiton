package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.ContestantRequest.CreateContestantRequest;
import com.schoolcompetition.model.dto.request.ContestantRequest.UpdateContestantRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/contestant-management")
public class ContestantController {
    @Autowired
    ContestantService contestantService;

    @GetMapping("contestants")
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return contestantService.getListContestants(page, size);
    }

    @GetMapping(value = {"contestant/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return contestantService.getById(id);
    }

    @PostMapping("contestant")
    public ResponseEntity<ResponseObj> createContestant(@RequestBody CreateContestantRequest contestantRequest) {
        return contestantService.createContestant(contestantRequest);
    }

    @PutMapping("contestant/{id}")
    public ResponseEntity<ResponseObj> updateContestant(@PathVariable int id, @RequestBody UpdateContestantRequest contestantRequest) {
        return contestantService.updateContestant(id, contestantRequest);
    }

    @PutMapping("contestant-status/{id}")
    public ResponseEntity<ResponseObj> deleteContestant(@PathVariable int id) {
        return contestantService.deleteContestant(id);
    }
}
