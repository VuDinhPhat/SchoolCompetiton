package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.model.entity.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultService {
    List<Result> getAllResult();
}
