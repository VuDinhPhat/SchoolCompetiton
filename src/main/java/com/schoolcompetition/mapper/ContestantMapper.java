package com.schoolcompetition.mapper;
import com.schoolcompetition.model.dto.response.ContestantResponse;
import com.schoolcompetition.model.entity.Contestant;
public class ContestantMapper {
    public static ContestantResponse toContestantResponse(Contestant contestant) {
        return ContestantResponse.builder()
                .id(contestant.getId())
                .student(contestant.getStudent())
                .coach(contestant.getCoach())
                .team(contestant.getTeam())
                .status(contestant.getStatus())
                .build();
    }
}
