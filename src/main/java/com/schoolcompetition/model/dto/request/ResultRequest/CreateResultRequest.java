package com.schoolcompetition.model.dto.request.ResultRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateResultRequest {
    @NotNull
    int score;

    @NotBlank
    String finishTime;

    @NotNull
    int contestantId;

    @NotNull
    int matchId;

    @NotNull
    int carId;

    @NotNull
    Status status;
}
