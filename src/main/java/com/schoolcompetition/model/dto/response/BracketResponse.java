package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.model.entity.Round;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BracketResponse {
    int id;
    String name;
    private Round round;
}
