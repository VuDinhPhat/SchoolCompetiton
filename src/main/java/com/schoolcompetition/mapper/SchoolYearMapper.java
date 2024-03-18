package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.SchoolYearResponse;
import com.schoolcompetition.model.entity.SchoolYear;

public class SchoolYearMapper {
    public static SchoolYearResponse toSchoolYearResponse(SchoolYear schoolYear) {
        return SchoolYearResponse.builder()
                .id(schoolYear.getId())
                .year(schoolYear.getYear())
                .status(schoolYear.getStatus())
                .build();
    }
}
