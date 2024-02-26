package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<School>>> getAllClass() {
        ApiResponse apiResponse = new ApiResponse();
        List<School> schools = schoolService.getAll();
        apiResponse.ok(schools);
        return ResponseEntity.ok(apiResponse);
    }
}
