package com.schoolcompetition.model.dto.request.CompetitionRequest;

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
}
