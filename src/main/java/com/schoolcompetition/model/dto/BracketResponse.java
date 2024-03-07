package com.schoolcompetition.model.dto;

import com.schoolcompetition.model.entity.Round;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)
public class BracketResponse {
    public int id;
    public String name;
    //private Round round;
}
