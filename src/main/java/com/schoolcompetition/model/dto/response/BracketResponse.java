package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.Round;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BracketResponse {
    int id;
    String name;
    Round round;
    Status status;
}
