package com.schoolcompetition.model.dto.request.BracketRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBracketRequest {
    @NotNull
    String name;
    @NotNull
    String roundId;
}
