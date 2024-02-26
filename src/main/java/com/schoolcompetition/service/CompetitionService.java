package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Competition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    List<Competition> getAllCompetition();
}
