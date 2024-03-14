package com.schoolcompetition.model.dto.request.ContestantRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateContestantRequest {
    @NotNull
    int studentId;

    @NotNull
    int coachId;

    @NotNull
    int teamId;
}
