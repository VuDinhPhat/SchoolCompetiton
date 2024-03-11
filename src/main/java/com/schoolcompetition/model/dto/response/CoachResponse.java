package com.schoolcompetition.model.dto.response;

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
    char sex;
    School school;

}
