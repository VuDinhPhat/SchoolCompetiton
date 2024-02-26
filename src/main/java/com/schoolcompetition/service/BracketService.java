package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BracketService {
    List<Bracket> getAllBracket();
}
