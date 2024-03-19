package com.schoolcompetition.model.dto.request.RoundRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRoundRequest {
    String name;
    String map;
    int competitionId;
    Status status;
}
