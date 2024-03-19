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
@RequestMapping("/schoolyear")
public class SchoolYearController {
    @Autowired
    SchoolYearService schoolYearService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return schoolYearService.getListSchoolsYear(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return schoolYearService.getSchoolYearById(id);
    }
    @GetMapping(value = {"getByYear"})
    public ResponseEntity<ResponseObj> getByYear(@RequestParam int year) { return schoolYearService.getSchoolYearByYear(year);
    }
    @PostMapping(value = {"create"})
    public ResponseEntity<ResponseObj> createSchoolYear(@RequestBody CreateSchoolYearRequest request) {
        return schoolYearService.createSchoolYear(request);
    }
    @PutMapping(value = {"update"})
    public ResponseEntity<ResponseObj> updateSchoolYear(@RequestParam int id, @RequestBody UpdateSchoolYearRequest requestSchoolYear) {
        return schoolYearService.updateSchoolYear(id, requestSchoolYear);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteSchoolYear(@RequestParam int id) {
        return schoolYearService.deleteSchoolYear(id);
    }
}
