package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.SchoolYear;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompetitionResponse {
    int id;
    String name;
    String description;
    String holdPlace;
    SchoolYear schoolYear;
}
