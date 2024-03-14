package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/school")
public class SchoolController {
    @Autowired
    SchoolService schoolService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ResponseObj> getAll() {
        return schoolService.getAllSchools();
    }

    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return schoolService.getSchoolById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return schoolService.getSchoolByName(name);}
}
