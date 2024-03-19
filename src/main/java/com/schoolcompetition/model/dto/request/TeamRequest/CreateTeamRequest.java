package com.schoolcompetition.model.dto.request.TeamRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTeamRequest {
    @NotBlank
    String name;

    @NotNull
    int coachId;

    @NotNull
    int competitionId;

    @NotNull
    Status status;
}
