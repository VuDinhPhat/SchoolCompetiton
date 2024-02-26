package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.ApiResponse;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.service.BracketService;
import com.schoolcompetition.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(value = {"getAll"})
    public ResponseEntity<ApiResponse<List<Car>>> getAllClass() {
        ApiResponse apiResponse = new ApiResponse();
        List<Car> cars = carService.getAllCar();
        apiResponse.ok(cars);
        return ResponseEntity.ok(apiResponse);
    }
}
