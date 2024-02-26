package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeam() {
        return teamRepository.findAll();
    }
}
