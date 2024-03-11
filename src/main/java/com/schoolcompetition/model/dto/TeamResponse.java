package com.schoolcompetition.model.dto;

import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.Competition;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TeamResponse {
    int id;
    String name;
    Coach coach;
    Competition competition;

}
