package com.schoolcompetition.model.dto.request.SchoolRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateSchoolRequest {
    @NotBlank
    String name;

    @NotBlank
    String address;

    Status status;
}
