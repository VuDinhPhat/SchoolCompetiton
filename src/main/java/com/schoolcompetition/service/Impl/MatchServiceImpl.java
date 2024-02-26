package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.repository.MatchRepository;
import com.schoolcompetition.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Override
    public List<Match> getAllMatch() {
        return matchRepository.findAll();
    }
}
