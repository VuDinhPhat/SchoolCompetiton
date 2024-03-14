package com.schoolcompetition.model.dto.request.ContestantRequest;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateContestantRequest {
    int studentId;
    int coachId;
    int teamId;
}
