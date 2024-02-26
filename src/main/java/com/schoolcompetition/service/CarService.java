package com.schoolcompetition.service;

import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    List<Car> getAllCar();
}
