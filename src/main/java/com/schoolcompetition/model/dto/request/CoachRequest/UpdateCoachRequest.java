package com.schoolcompetition.model.dto.request.CoachRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCoachRequest {
    String name;

    Date dob;

    char sex;

    int schoolId;
}
