package com.schoolcompetition.model.dto.request.SchoolYearRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSchoolYearRequest {
    int year;

    Status status;
}
