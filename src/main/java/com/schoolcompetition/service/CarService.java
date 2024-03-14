package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.SchoolYear;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    ResponseEntity<ResponseObj> getAllCar();

    ResponseEntity<ResponseObj> getCarById(int id);
}
