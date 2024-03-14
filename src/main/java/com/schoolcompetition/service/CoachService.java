package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachService {
    ResponseEntity<ResponseObj> getAllCoach();

    ResponseEntity<ResponseObj> getCoachById(int id);
    ResponseEntity<ResponseObj> getCoachByName(String name);
}
