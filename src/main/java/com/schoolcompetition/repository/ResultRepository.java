package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ResultRepository extends JpaRepository<Result, Integer> {
}
