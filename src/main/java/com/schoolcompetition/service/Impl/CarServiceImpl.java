package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CarMapper;
import com.schoolcompetition.model.dto.response.CarResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.repository.CarRepository;
import com.schoolcompetition.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;
    @Override
    public ResponseEntity<ResponseObj> getAllCar() {
        List<Car> carList = carRepository.findAll();
        List<CarResponse> carResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Car car : carList) {
            carResponses.add(CarMapper.toCarResponse(car));
        }
        response.put("Car", carResponses);

        if (!carResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Load all Car successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Car failed")
                .data(null)
                .build();
        return ResponseEntity.ok().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getCarById(int id) {
        Car car = carRepository.getReferenceById(id);

        if (car != null) {
            CarResponse carResponse = CarMapper.toCarResponse(car);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Car founded")
                    .data(carResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }
}
