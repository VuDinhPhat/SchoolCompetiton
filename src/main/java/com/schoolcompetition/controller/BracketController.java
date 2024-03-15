package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.BracketRequest.CreateBracketRequest;
import com.schoolcompetition.model.dto.request.BracketRequest.UpdateBracketRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.service.BracketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bracket")
public class BracketController {
    @Autowired
    BracketService bracketService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return bracketService.getAllBracket();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return bracketService.getBracketById(id);
    }

    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getById(@RequestParam String name) {
        return bracketService.getByName(name);
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseObj> createBracket(@RequestBody CreateBracketRequest bracketRequest) {
        return bracketService.createBracket(bracketRequest);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObj> updateBracket(@PathVariable int id, @RequestBody UpdateBracketRequest bracketRequest) {
        return bracketService.updateBracket(id, bracketRequest);
    }
}
