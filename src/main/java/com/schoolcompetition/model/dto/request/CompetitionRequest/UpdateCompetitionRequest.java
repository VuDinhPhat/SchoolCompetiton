package com.schoolcompetition.model.dto.request.CompetitionRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCompetitionRequest {
    String name;

    String description;

    String holdPlace;

    int schoolYearId;

    Status status;
}
