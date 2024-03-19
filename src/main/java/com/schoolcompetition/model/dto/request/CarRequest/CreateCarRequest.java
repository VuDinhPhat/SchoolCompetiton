package com.schoolcompetition.model.dto.request.CarRequest;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.model.entity.Team;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCarRequest {
    @NotBlank
    String name;

    @NotBlank
    String type;

    @NotBlank
    String description;

    @NotNull
    int teamId;

    @NotNull
    Status status;

}
