package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.service.CarService;
import com.schoolcompetition.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {
    @Autowired
    CoachService coachService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Coach>>> getAll() {
        ApiResponse apiResponse = new ApiResponse();
        List<Coach> coaches = coachService.getAllCoach();
        apiResponse.ok(coaches);
        return ResponseEntity.ok(apiResponse);
    }
}
