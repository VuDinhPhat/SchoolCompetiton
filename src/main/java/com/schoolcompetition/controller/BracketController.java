package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.BracketRequest.CreateBracketRequest;

import com.schoolcompetition.model.dto.request.BracketRequest.UpdateBracketRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.service.BracketService;
import com.schoolcompetition.util.ValidatorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/bracket-management")
public class BracketController {
    @Autowired
    BracketService bracketService;

    @GetMapping(value = {"brackets"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return bracketService.getListBrackets(page, size);
    }

    @GetMapping(value = {"bracket/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return bracketService.getBracketById(id);
    }

    @GetMapping(value = {"bracket/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) {
        return bracketService.getByName(name);
    }


    @PostMapping(value = {"bracket"})
    public ResponseEntity<ResponseObj> createBracket(@Valid @RequestBody CreateBracketRequest createBracketRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            HashMap<String, String> errors = ValidatorUtil.toErrors(bindingResult.getFieldErrors());
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Fail to create given Bracket")
                    .data(errors)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
        return bracketService.createBracket(createBracketRequest, bindingResult);
    }

    @PutMapping("bracket/{id}")
    public ResponseEntity<ResponseObj> updateBracket(@PathVariable int id, @RequestBody UpdateBracketRequest bracketRequest) {
        return bracketService.updateBracket(id, bracketRequest);
    }

    @PutMapping("bracket-status/{id}")
    public ResponseEntity<ResponseObj> deleteBracket(@PathVariable int id) {
        return bracketService.deleteBracket(id);
    }
}
