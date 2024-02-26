package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.repository.BracketRepository;
import com.schoolcompetition.service.BracketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BracketServiceImpl implements BracketService {
    @Autowired
    BracketRepository bracketRepository;

    @Override
    public List<Bracket> getAllBracket() {
        return bracketRepository.findAll();
    }


}
