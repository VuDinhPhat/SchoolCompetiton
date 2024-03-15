package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CarMapper;
import com.schoolcompetition.model.dto.request.CarRequest.CreateCarRequest;
import com.schoolcompetition.model.dto.request.CarRequest.UpdateCarRequest;
import com.schoolcompetition.model.dto.response.CarResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.repository.CarRepository;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.CarService;
import jakarta.transaction.Transactional;
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
    @Autowired
    TeamRepository teamRepository;
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
        List<Car> carList = carRepository.findAll();

        for (Car c : carList) {
            if (c.equals(car)) {
                CarResponse carResponse = CarMapper.toCarResponse(car);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Car founded")
                        .data(carResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getCarByName(String name) {
        List<Car> carList = carRepository.findAll();
        List<CarResponse> carResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Car car : carList) {
            if (car.getName().toLowerCase().contains(name.toLowerCase())) {
                carResponses.add(CarMapper.toCarResponse(car));
            }
        }

        response.put("Cars", carResponses);

        if (!carResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + carResponses.size() + " record(s) matching")
                    .data(response)
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

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createCar(CreateCarRequest carRequest) {
        try {
            // Kiểm tra xem teamId trong request có tồn tại không
            Team team = teamRepository.findById(carRequest.getTeamId()).orElse(null);
            if (team == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Team not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Tạo xe mới
            Car car = new Car();
            car.setName(carRequest.getName());
            car.setType(carRequest.getType());
            car.setDescription(carRequest.getDescription());
            car.setTeam(team);

            // Lưu xe mới vào cơ sở dữ liệu
            Car savedCar = carRepository.save(car);

            // Chuyển đổi xe thành đối tượng response
            CarResponse carResponse = CarMapper.toCarResponse(savedCar);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Car created successfully")
                    .data(carResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create car")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateCar(int id, UpdateCarRequest carRequest) {
        try {
            // Tìm xe cần cập nhật trong cơ sở dữ liệu
            Car car = carRepository.findById(id).orElse(null);
            if (car == null) {
                // Trả về response not found nếu không tìm thấy xe
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Car not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Cập nhật thông tin xe nếu các trường không null
            if (carRequest.getName() != null) {
                car.setName(carRequest.getName());
            }
            if (carRequest.getType() != null) {
                car.setType(carRequest.getType());
            }
            if (carRequest.getDescription() != null) {
                car.setDescription(carRequest.getDescription());
            }
            if (carRequest.getTeamId() != 0) {
                Team team = teamRepository.findById(carRequest.getTeamId()).orElse(null);
                if (team == null) {
                    // Trả về response not found nếu không tìm thấy đội
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Team not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                car.setTeam(team);
            }

            // Lưu xe đã cập nhật vào cơ sở dữ liệu
            Car updatedCar = carRepository.save(car);

            // Chuyển đổi xe đã cập nhật thành đối tượng response
            CarResponse carResponse = CarMapper.toCarResponse(updatedCar);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Car updated successfully")
                    .data(carResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update car")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }


}
