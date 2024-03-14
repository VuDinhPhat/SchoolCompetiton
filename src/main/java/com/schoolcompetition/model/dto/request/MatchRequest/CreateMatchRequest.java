package com.schoolcompetition.model.dto.request.MatchRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;

    @NotBlank
    String place;

    @NotBlank
    int lap;

    @NotNull
    int bracketId;
}
