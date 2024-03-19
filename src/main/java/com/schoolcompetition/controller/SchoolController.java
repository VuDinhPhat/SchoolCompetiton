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
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return schoolService.getListSchools(page, size);
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return schoolService.getSchoolById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return schoolService.getSchoolByName(name);}
    @PostMapping(value = {"create"})
    public ResponseEntity<ResponseObj> createSchool(@RequestBody CreateSchoolRequest request) {
        return schoolService.createSchool(request);
    }
    @PutMapping(value = {"update"})
    public ResponseEntity<ResponseObj> updateSchool(@RequestParam int id, @RequestBody UpdateSchoolRequest requestSchool) {
        return schoolService.updateSchool(id, requestSchool);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteSchool(@PathVariable int id) {
        return schoolService.deleteSchool(id);
    }
}
