package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.model.entity.Team;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeamService {
    List<Team> getAllTeam();
}
