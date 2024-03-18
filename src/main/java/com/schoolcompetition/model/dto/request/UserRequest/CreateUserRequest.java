package com.schoolcompetition.model.dto.request.UserRequest;

import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserRequest {
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String name;

    String phone;

    Status status;
}
