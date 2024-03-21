package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.SchoolYearRequest.CreateSchoolYearRequest;
import com.schoolcompetition.model.dto.request.SchoolYearRequest.UpdateSchoolYearRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.service.SchoolYearService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schoolyear-management")
public class SchoolYearController {
    @Autowired
    SchoolYearService schoolYearService;

    @GetMapping(value = {"schoolyears"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return schoolYearService.getListSchoolsYear(page, size);
    }

    @GetMapping(value = {"schoolyear/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return schoolYearService.getSchoolYearById(id);
    }

    @GetMapping(value = {"schoolyear/year/{year}"})
    public ResponseEntity<ResponseObj> getByYear(@PathVariable int year) { return schoolYearService.getSchoolYearByYear(year);
    }

    @PostMapping(value = {"schoolyear"})
    public ResponseEntity<ResponseObj> createSchoolYear(@RequestBody CreateSchoolYearRequest request) {
        return schoolYearService.createSchoolYear(request);
    }

    @PutMapping(value = {"schoolyear/{id}"})
    public ResponseEntity<ResponseObj> updateSchoolYear(@PathVariable int id, @RequestBody UpdateSchoolYearRequest requestSchoolYear) {
        return schoolYearService.updateSchoolYear(id, requestSchoolYear);
    }

    @PutMapping("schoolyear-status/{id}")
    public ResponseEntity<ResponseObj> deleteSchoolYear(@PathVariable int id) {
        return schoolYearService.deleteSchoolYear(id);
    }
    @GetMapping("total")
    public int countTotalSchoolYear(){return schoolYearService.countTotalSchoolYear();}
}
