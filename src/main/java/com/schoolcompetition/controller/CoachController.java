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
@RequestMapping("/api/coach-management")
public class CoachController {
    @Autowired
    CoachService coachService;

    @GetMapping(value = {"coaches"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return coachService.getListCoaches(page, size);
    }


    @GetMapping(value = {"coach/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return coachService.getCoachById(id);
    }

    @GetMapping(value = {"coach/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return coachService.getCoachByName(name);}

    @PostMapping("coach")
    public ResponseEntity<ResponseObj> createCoach(@RequestBody CreateCoachRequest createCoachRequest) {
        return coachService.createCoach(createCoachRequest);
    }

    @PutMapping("coach/{id}")
    public ResponseEntity<ResponseObj> updateCoach(@PathVariable int id, @RequestBody UpdateCoachRequest updateCoachRequest) {
        return coachService.updateCoach(id, updateCoachRequest);
    }

    @PutMapping("coach-status/{id}")
    public ResponseEntity<ResponseObj> deleteCoach(@PathVariable int id) {
        return coachService.deleteCoach(id);
    }
}
