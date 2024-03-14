package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.MatchResponse;
import com.schoolcompetition.model.entity.Match;

public class MatchMapper {
    public static MatchResponse toMatchResponse(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .name(match.getName())
                .startTime(match.getStartTime())
                .place(match.getPlace())
                .lap(match.getLap())
                .bracket(match.getBracket())
                .build();
    }
}
