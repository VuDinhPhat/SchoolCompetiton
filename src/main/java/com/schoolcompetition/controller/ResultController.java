package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.ResultRequest.CreateResultRequest;
import com.schoolcompetition.model.dto.request.ResultRequest.UpdateResultRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/result")
public class ResultController {
    @Autowired
    ResultService resultService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return resultService.getListResults(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return resultService.getResultById(id);
    }

    @PostMapping("create")
    public ResponseEntity<ResponseObj> createResult(@RequestBody CreateResultRequest resultRequest) {
        return resultService.createResult(resultRequest);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateResult(@PathVariable int id, @RequestBody UpdateResultRequest resultRequest) {
        return resultService.updateResult(id, resultRequest);
    }
    @PutMapping("/delete")
    public ResponseEntity<ResponseObj> deleteResult(@PathVariable int id) {
        return resultService.deleteResult(id);
    }
}
