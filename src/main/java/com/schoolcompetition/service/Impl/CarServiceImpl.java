package com.schoolcompetition.service.Impl;

import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.repository.CarRepository;
import com.schoolcompetition.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;
    @Override
    public List<Car> getAllCar() {
        return carRepository.findAll();
    }
}
