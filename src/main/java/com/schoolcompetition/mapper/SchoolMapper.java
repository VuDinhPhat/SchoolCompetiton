package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.SchoolResponse;
import com.schoolcompetition.model.entity.School;

public class SchoolMapper {
    public static SchoolResponse toSchoolResponse(School school) {
        return SchoolResponse.builder()
                .id(school.getId())
                .name(school.getName())
                .address(school.getAddress())
                .status(school.getStatus())
                .build();
    }
}
