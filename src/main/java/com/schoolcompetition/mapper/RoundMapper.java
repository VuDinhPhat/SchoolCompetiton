package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.RoundResponse;
import com.schoolcompetition.model.entity.Round;

public class RoundMapper {
    public static RoundResponse toRoundResponse(Round round) {
        return RoundResponse.builder()
                .id(round.getId())
                .name(round.getName())
                .map(round.getMap())
                .competition(round.getCompetition())
                .status(round.getStatus())
                .build();
    }
}
