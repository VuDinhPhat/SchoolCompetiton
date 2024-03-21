package com.schoolcompetition.controller;

import com.schoolcompetition.model.dto.request.CarRequest.CreateCarRequest;
import com.schoolcompetition.model.dto.request.CarRequest.UpdateCarRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ApiResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car-management")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(value = {"cars"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return carService.getListCars(page, size);
    }


    @GetMapping(value = {"car/{id}"})
    public ResponseEntity<ResponseObj> getById(@PathVariable int id) {
        return carService.getCarById(id);
    }
    @GetMapping(value = {"car/{name}"})
    public ResponseEntity<ResponseObj> getByName(@PathVariable String name) { return carService.getCarByName(name);
    }
    @PostMapping("car")
    public ResponseEntity<ResponseObj> createCar(@RequestBody CreateCarRequest carRequest) {
        return carService.createCar(carRequest);
    }
    @PutMapping("car/{id}")
    public ResponseEntity<ResponseObj> updateCar(@PathVariable int id, @RequestBody UpdateCarRequest carRequest) {
        return carService.updateCar(id, carRequest);
    }
    @PutMapping("car-status/{id}")
    public ResponseEntity<ResponseObj> deleteCar(@PathVariable int id) {
        return carService.deleteCar(id);
    }
}
