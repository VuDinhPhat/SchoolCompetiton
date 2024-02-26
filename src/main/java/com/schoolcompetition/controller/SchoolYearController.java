package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.service.SchoolService;
import com.schoolcompetition.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/schoolyear")
public class SchoolYearController {
    @Autowired
    SchoolYearService schoolYearService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<SchoolYear>>> getAllClass() {
        ApiResponse apiResponse = new ApiResponse();
        List<SchoolYear> schoolYears = schoolYearService.getAll();
        apiResponse.ok(schoolYears);
        return ResponseEntity.ok(apiResponse);
    }
}
