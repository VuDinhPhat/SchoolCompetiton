package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.repository.RoundRepository;
import com.schoolcompetition.service.RoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoundServiceImpl implements RoundService {
    @Autowired
    RoundRepository roundRepository;

    @Override
    public List<Round> getAllRound() {
        return roundRepository.findAll();
    }
}
