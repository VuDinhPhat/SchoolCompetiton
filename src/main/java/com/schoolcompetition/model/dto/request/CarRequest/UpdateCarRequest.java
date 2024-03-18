package com.schoolcompetition.model.dto.request.CarRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCarRequest {
    String name;

    String type;

    String description;

    int teamId;

    Status status;
}
