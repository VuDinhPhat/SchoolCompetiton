package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Gender;
import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.School;
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
public class CoachResponse {
    int id;
    String name;
    Date dob;
    Gender sex;
    School school;
    Status status;

}
