package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Contestant;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContestantService {
    List<Contestant> getAll();
}
