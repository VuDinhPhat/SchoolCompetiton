package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.repository.ContestantRepository;
import com.schoolcompetition.service.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContestantServiceImpl implements ContestantService {
    @Autowired
    ContestantRepository contestantRepository;
    @Override
    public List<Contestant> getAll() {
        return contestantRepository.findAll();
    }
}
