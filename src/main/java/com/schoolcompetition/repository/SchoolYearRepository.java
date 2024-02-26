package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer> {
}

