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
@RequestMapping("/car")
public class CarController {
    @Autowired
    CarService carService;

    @GetMapping(value = {"getList"})
    public ResponseEntity<ResponseObj> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return carService.getListCars(page, size);
    }


    @GetMapping(value = {"getById"})
    public ResponseEntity<ResponseObj> getById(@RequestParam int id) {
        return carService.getCarById(id);
    }
    @GetMapping(value = {"getByName"})
    public ResponseEntity<ResponseObj> getByName(@RequestParam String name) { return carService.getCarByName(name);
    }
    @PostMapping("create")
    public ResponseEntity<ResponseObj> createCar(@RequestBody CreateCarRequest carRequest) {
        return carService.createCar(carRequest);
    }
    @PutMapping("update")
    public ResponseEntity<ResponseObj> updateCar(@PathVariable int id, @RequestBody UpdateCarRequest carRequest) {
        return carService.updateCar(id, carRequest);
    }
    @PutMapping("delete")
    public ResponseEntity<ResponseObj> deleteCar(@PathVariable int id) {
        return carService.deleteCar(id);
    }
}
