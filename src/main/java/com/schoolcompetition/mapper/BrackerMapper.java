package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.BracketResponse;
import com.schoolcompetition.model.entity.Bracket;

public class BrackerMapper {
    public static BracketResponse toBracketResponse(Bracket bracket) {
        return BracketResponse.builder()
                .id(bracket.getId())
                .name(bracket.getName())
                //.round(bracket.getRound())
                .build();
    }
}
