package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.Bracket;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MatchResponse {
    int id;
    String name;
    Date startTime;
    String place;
    int lap;
    Bracket bracket;
}
