package com.schoolcompetition.model.dto.request.CoachRequest;
import com.schoolcompetition.enums.Gender;
import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.School;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCoachRequest {
    @NotBlank
    String name;

    @NotNull
    Date dob;

    @NotNull
    Gender sex;

    @NotNull
    Status status;

    @NotNull
    int schoolId;
}
