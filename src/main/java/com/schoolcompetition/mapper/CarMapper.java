package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.CarResponse;
import com.schoolcompetition.model.entity.Car;

public class CarMapper {
    public static CarResponse toCarResponse(Car car) {


        return CarResponse.builder()
                .id(car.getId())
                .name(car.getName())
                .type(car.getType())
                .description(car.getDescription())
                .team(car.getTeam())
                .status(car.getStatus())
                .build();
    }
}
