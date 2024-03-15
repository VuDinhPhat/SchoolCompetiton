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
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) {
        return bracketService.getByName(name);
    }


    @PostMapping(value = {"createBracket"})
    public ResponseEntity<ResponseObj> createBracket(@RequestBody @Valid CreateBracketRequest createBracketRequest, BindingResult bindingResult) {
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

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObj> updateBracket(@PathVariable int id, @RequestBody UpdateBracketRequest bracketRequest) {
        return bracketService.updateBracket(id, bracketRequest);
    }
}
