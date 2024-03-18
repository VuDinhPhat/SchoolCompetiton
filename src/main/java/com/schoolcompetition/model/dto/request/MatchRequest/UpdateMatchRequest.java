package com.schoolcompetition.model.dto.request.MatchRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMatchRequest {
    String name;

    Date startTime;

    String place;

    int lap;

    int bracketId;

    Status status;
}