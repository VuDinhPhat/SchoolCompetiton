package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.TeamResponse;
import com.schoolcompetition.model.entity.Team;

public class TeamMapper {
    public static TeamResponse toTeamResponse(Team team) {
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .coach(team.getCoach())
                .competition(team.getCompetition())
                .status(team.getStatus())
                .build();
    }
}
