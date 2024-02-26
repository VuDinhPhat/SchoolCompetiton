package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.repository.CoachRepository;
import com.schoolcompetition.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoachServiceImpl implements CoachService {
    @Autowired
    CoachRepository coachRepository;
    @Override
    public List<Coach> getAllCoach() {
        return coachRepository.findAll();
    }
}
