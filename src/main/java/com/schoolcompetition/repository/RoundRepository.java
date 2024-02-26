package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoundRepository extends JpaRepository<Round, Integer> {
}
