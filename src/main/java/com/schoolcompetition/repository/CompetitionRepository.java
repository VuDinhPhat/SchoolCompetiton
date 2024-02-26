package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
}
