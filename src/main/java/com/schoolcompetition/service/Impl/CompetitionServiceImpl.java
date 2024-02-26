package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.repository.CompetitionRepository;
import com.schoolcompetition.service.CompetitionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;

    @Override
    public List<Competition> getAllCompetition(){
        return competitionRepository.findAll();
    }
}
