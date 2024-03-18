package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.UserResponse;
import com.schoolcompetition.model.entity.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .status(user.getStatus())
                .build();
    }
}
