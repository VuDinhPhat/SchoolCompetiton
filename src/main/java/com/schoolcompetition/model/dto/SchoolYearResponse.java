package com.schoolcompetition.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PUBLIC)

public class SchoolYearResponse {
    public long id;
    public int year;
}
