package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CoachService {
    List<Coach> getAllCoach();
}
