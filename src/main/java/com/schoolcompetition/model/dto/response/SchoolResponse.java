package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SchoolResponse {
    int id;
    String name;
    String address;
    Status status;
}
