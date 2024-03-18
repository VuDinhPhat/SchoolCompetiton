package com.schoolcompetition.model.dto.request.UserRequest;

import com.schoolcompetition.enums.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserRequest {
    String email;

    String password;

    String name;

    String phone;

    Status status;
}
