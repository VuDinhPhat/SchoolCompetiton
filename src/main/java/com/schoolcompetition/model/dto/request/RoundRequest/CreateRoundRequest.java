package com.schoolcompetition.model.dto.request.RoundRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateRoundRequest {
    @NotBlank
    String name;

    @NotBlank
    String map;

    @NotNull
    int competitionId;
}
