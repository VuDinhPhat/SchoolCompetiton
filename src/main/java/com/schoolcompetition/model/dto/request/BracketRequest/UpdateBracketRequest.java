package com.schoolcompetition.model.dto.request.BracketRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBracketRequest {
    String name;
    int roundId;
    Status status;
}
