package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.repository.ResultRepository;
import com.schoolcompetition.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository resultRepository;

    @Override
    public List<Result> getAllResult() {
        return resultRepository.findAll();
    }
}
