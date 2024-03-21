package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.SchoolRequest.CreateSchoolRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.UpdateSchoolRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/school-management")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping(value = {"schools"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return schoolService.getListSchools(page, size);
    }

    @GetMapping(value = {"school/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return schoolService.getSchoolById(id);
    }
    @GetMapping(value = {"school/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return schoolService.getSchoolByName(name);}
    @PostMapping(value = {"school"})
    public ResponseEntity<ResponseObj> createSchool(@RequestBody CreateSchoolRequest request) {
        return schoolService.createSchool(request);
    }
    @PutMapping(value = {"school/{id}"})
    public ResponseEntity<ResponseObj> updateSchool(@PathVariable int id, @RequestBody UpdateSchoolRequest requestSchool) {
        return schoolService.updateSchool(id, requestSchool);
    }
    @PutMapping("school-status/{id}")
    public ResponseEntity<ResponseObj> deleteSchool(@PathVariable int id) {
        return schoolService.deleteSchool(id);
    }
}
