package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.model.entity.Round;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoundService {
    List<Round> getAllRound();
}
