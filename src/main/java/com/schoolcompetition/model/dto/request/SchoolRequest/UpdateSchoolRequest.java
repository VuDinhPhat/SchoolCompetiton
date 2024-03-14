package com.schoolcompetition.model.dto.request.SchoolRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateSchoolRequest {
    String name;
    String address;
}
