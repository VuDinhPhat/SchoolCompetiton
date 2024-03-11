package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.CompetitionResponse;
import com.schoolcompetition.model.entity.Competition;

public class CompetitionMapper {
    public static CompetitionResponse toCompetitionResponse(Competition competition) {
        return CompetitionResponse.builder()
                .id(competition.getId())
                .name(competition.getName())
                .description(competition.getDescription())
                .holdPlace(competition.getHoldPlace())
                .schoolYear(competition.getSchoolYear())
                .build();
    }
}
