package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.CoachRequest.CreateCoachRequest;
import com.schoolcompetition.model.dto.request.CoachRequest.UpdateCoachRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.CreateSchoolRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.UpdateSchoolRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachService {
    ResponseEntity<ResponseObj> getListCoaches(int page, int size);

    ResponseEntity<ResponseObj> getCoachById(int id);
    ResponseEntity<ResponseObj> getCoachByName(String name);
    ResponseEntity<ResponseObj> createCoach(CreateCoachRequest coachRequest);
    ResponseEntity<ResponseObj> updateCoach(int id, UpdateCoachRequest coachRequest);
    ResponseEntity<ResponseObj> deleteCoach(int id);

    int countTotalCoach();
}
