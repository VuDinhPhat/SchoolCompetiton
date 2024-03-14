package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.CarRequest.CreateCarRequest;
import com.schoolcompetition.model.dto.request.CarRequest.UpdateCarRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
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
    ResponseEntity<ResponseObj> getCarByName(String name);
    ResponseEntity<ResponseObj> createCar(CreateCarRequest carRequest);
    ResponseEntity<ResponseObj> updateCar(int id, UpdateCarRequest carRequest);
}
