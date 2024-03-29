package com.schoolcompetition.model.dto.request.BracketRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PUBLIC)
public class CreateBracketRequest {

    @NotNull(message = "Name must not be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Names must not include numbers")
    public String name;

    @NotNull
    int roundId;


    @NotNull
    Status status;
}
