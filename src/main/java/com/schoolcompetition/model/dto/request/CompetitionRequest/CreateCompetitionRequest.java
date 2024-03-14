package com.schoolcompetition.model.dto.request.CompetitionRequest;

import com.schoolcompetition.model.entity.SchoolYear;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCompetitionRequest {
    @NotBlank
    String name;

    @NotBlank
    String description;

    @NotBlank
    String holdPlace;

    @NotNull
    int schoolYearId;
}
