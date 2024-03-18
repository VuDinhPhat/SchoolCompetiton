package com.schoolcompetition.model.dto.response;

import com.schoolcompetition.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String email;
    String password;
    String name;
    String phone;
    Status status;

}
