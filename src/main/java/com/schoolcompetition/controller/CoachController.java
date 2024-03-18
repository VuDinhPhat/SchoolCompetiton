package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.CoachRequest.CreateCoachRequest;
import com.schoolcompetition.model.dto.request.CoachRequest.UpdateCoachRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coach")
public class CoachController {
    @Autowired
    CoachService coachService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return coachService.getListCoaches(page, size);
    }


    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return coachService.getCoachById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return coachService.getCoachByName(name);}
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createCoach(@RequestBody CreateCoachRequest createCoachRequest) {
        return coachService.createCoach(createCoachRequest);
    }

    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateCoach(@PathVariable int id, @RequestBody UpdateCoachRequest updateCoachRequest) {
        return coachService.updateCoach(id, updateCoachRequest);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteCoach(@PathVariable int id) {
        return coachService.deleteCoach(id);
    }
}
