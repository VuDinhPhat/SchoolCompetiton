package com.schoolcompetition.model.dto.request.ResultRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateResultRequest {
    int score;
    String finishTime;
    int contestantId;
    int matchId;
    int carId;
    Status status;
}
