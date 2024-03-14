package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.CoachResponse;
import com.schoolcompetition.model.entity.Coach;

public class CoachMapper {
    public static CoachResponse toCoachResponse(Coach coach) {
        return CoachResponse.builder()
                .id(coach.getId())
                .name(coach.getName())
                .dob(coach.getDob())
                .sex(coach.getSex())
                .school(coach.getSchool())
                .build();
    }
}
