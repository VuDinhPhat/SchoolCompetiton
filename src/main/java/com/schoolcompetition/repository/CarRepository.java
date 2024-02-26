package com.schoolcompetition.repository;

import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface CarRepository extends JpaRepository<Car, Integer> {
}
