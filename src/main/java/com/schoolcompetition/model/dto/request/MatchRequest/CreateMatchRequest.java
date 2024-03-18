package com.schoolcompetition.model.dto.request.MatchRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateMatchRequest {
    @NotBlank
    String name;

    @NotNull
    Date startTime;

    @NotBlank
    String place;

    @NotBlank
    int lap;

    @NotNull
    int bracketId;

    @NotNull
    Status status;
}
