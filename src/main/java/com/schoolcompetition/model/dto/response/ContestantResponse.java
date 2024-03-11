package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.Student;
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
public class ContestantResponse {
    int id;
    Student student;
    Coach coach;
    Team team;
}
