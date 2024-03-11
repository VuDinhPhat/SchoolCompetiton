package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.Competition;
import lombok.*;
import lombok.experimental.FieldDefaults;

//@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoundResponse {
    int id;
    String name;
    String map;
    Competition competition;
}
