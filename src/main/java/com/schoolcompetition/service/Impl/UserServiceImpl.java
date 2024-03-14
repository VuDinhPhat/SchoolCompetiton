package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.UserMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.UserResponse;
import com.schoolcompetition.model.entity.User;
import com.schoolcompetition.repository.UserRepository;
import com.schoolcompetition.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseObj> getAll() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (User user : userList) {
            userResponses.add(UserMapper.toUserResponse(user));
        }
        response.put("User", userResponses);

        if (!userResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all User successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all User failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getById(int id) {
        User user = userRepository.getReferenceById(id);

        if (user != null) {
            UserResponse userResponse = UserMapper.toUserResponse(user);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Login successfully")
                    .data(userResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Email or Password incorrect")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> login(String email, String password) {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (email.equals(user.getEmail()) && password.equals(user.getPassword())) {
                UserResponse userResponse = UserMapper.toUserResponse(userRepository.getReferenceById(user.getId()));
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Login successfully")
                        .data(userResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Email or Password incorrect")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }
}
