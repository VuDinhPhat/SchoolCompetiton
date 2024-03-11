package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.model.entity.Match;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultResponse {
    int id;
    int score;
    String finishTime;
    Contestant contestant;
    Match match;
}
