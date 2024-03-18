package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.Team;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarResponse {
    int id;
    String name;
    String type;
    String description;
    Team team;
    Status status;
}
