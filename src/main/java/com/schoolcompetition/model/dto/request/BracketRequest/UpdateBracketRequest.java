package com.schoolcompetition.model.dto.request.BracketRequest;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateBracketRequest {
    String name;
    String roundId;
}
