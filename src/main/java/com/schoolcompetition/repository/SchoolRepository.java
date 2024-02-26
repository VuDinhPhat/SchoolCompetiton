package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SchoolRepository extends JpaRepository<School, Integer> {
}
