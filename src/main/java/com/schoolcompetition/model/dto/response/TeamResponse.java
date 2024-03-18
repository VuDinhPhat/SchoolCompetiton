package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.Competition;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeamResponse {
    int id;
    String name;
    Coach coach;
    Competition competition;
    Status status;
}
