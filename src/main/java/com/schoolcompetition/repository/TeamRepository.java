package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface TeamRepository extends JpaRepository<Team, Integer> {
}
