package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<ResponseObj> getListCars(int page, int size) {
        try {
            // Tạo đối tượng Pageable để xác định trang và kích thước trang
            Pageable pageable = PageRequest.of(page, size);

            // Truy vấn dữ liệu Car từ cơ sở dữ liệu sử dụng phân trang
            Page<Car> carPage = carRepository.findAll(pageable);

            // Kiểm tra xem trang có dữ liệu không
            if (carPage.hasContent()) {
                List<CarResponse> carResponses = new ArrayList<>();

                // Chuyển đổi danh sách Car thành danh sách CarResponse
                for (Car car : carPage.getContent()) {
                    carResponses.add(CarMapper.toCarResponse(car));
                }

                // Tạo đối tượng ResponseObj chứa danh sách CarResponse
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Car successfully")
                        .data(carResponses)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            } else {
                // Trả về thông báo rằng không có dữ liệu nào được tìm thấy trên trang cụ thể
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("No data found on page " + page)
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về thông báo lỗi nếu có vấn đề xảy ra khi lấy dữ liệu
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message("Failed to load Car data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
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
            car.setStatus(carRequest.getStatus());
            car.setTeam(team);

            Car savedCar = carRepository.save(car);

            CarResponse carResponse = CarMapper.toCarResponse(savedCar);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Car created successfully")
                    .data(carResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
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
            Car car = carRepository.findById(id).orElse(null);
            if (car == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Car not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (carRequest.getName() != null) {
                car.setName(carRequest.getName());
            }
            if (carRequest.getType() != null) {
                car.setType(carRequest.getType());
            }
            if (carRequest.getDescription() != null) {
                car.setDescription(carRequest.getDescription());
            }
            if (carRequest.getStatus() != null) {
                car.setStatus(carRequest.getStatus());
            }
            if (carRequest.getTeamId() != 0) {
                Team team = teamRepository.findById(carRequest.getTeamId()).orElse(null);
                if (team == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Team not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                car.setTeam(team);
            }

            Car updatedCar = carRepository.save(car);

            CarResponse carResponse = CarMapper.toCarResponse(updatedCar);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Car updated successfully")
                    .data(carResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update car")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteCar(int id) {
        Car carToDelete = carRepository.getReferenceById(id);
        List<Car> carList = carRepository.findAll();

        for (Car car : carList) {
            if (car.equals(carToDelete)) {
                car.setStatus(Status.IN_ACTIVE);
                carRepository.save(car);

                CarResponse carResponse = CarMapper.toCarResponse(carToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Car status changed to INACTIVE")
                        .data(carResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Car not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }



}
