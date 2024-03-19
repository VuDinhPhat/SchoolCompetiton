package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.UserMapper;
import com.schoolcompetition.model.dto.request.UserRequest.CreateUserRequest;
import com.schoolcompetition.model.dto.request.UserRequest.UpdateUserRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.UserResponse;
import com.schoolcompetition.model.entity.User;
import com.schoolcompetition.repository.UserRepository;
import com.schoolcompetition.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseObj> getListUsers(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<User> userPage = userRepository.findAll(pageable);

            if (userPage.hasContent()) {
                List<UserResponse> userResponses = new ArrayList<>();

                for (User user : userPage.getContent()) {
                    userResponses.add(UserMapper.toUserResponse(user));
                }

                Map<String, Object> response = new HashMap<>();
                response.put("User", userResponses);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Users successfully")
                        .data(response)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            } else {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("No data found on page " + page)
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message("Failed to load Users")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
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

    @Override
    public ResponseEntity<ResponseObj> deleteUser(int id) {
        User userToDelete = userRepository.getReferenceById(id);
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (user.equals(userToDelete)) {
                user.setStatus(Status.IN_ACTIVE);
                userRepository.save(user);

                UserResponse userResponse = UserMapper.toUserResponse(userToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("User status changed to INACTIVE")
                        .data(userResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("User not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }
    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createUser(CreateUserRequest userRequest) {
        try {
            User user = new User();
            user.setEmail(userRequest.getEmail());
            user.setPassword(userRequest.getPassword());
            user.setName(userRequest.getName());
            user.setPhone(userRequest.getPhone());
            user.setStatus(userRequest.getStatus());

            User savedUser = userRepository.save(user);

            UserResponse userResponse = UserMapper.toUserResponse(savedUser);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("User created successfully")
                    .data(userResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create user")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateUser(int id, UpdateUserRequest userRequest) {
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("User not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (userRequest.getEmail() != null) {
                user.setEmail(userRequest.getEmail());
            }
            if (userRequest.getPassword() != null) {
                user.setPassword(userRequest.getPassword());
            }
            if (userRequest.getName() != null) {
                user.setName(userRequest.getName());
            }
            if (userRequest.getPhone() != null) {
                user.setPhone(userRequest.getPhone());
            }
            if (userRequest.getStatus() != null) {
                user.setStatus(userRequest.getStatus());
            }

            User updatedUser = userRepository.save(user);

            UserResponse userResponse = UserMapper.toUserResponse(updatedUser);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("User updated successfully")
                    .data(userResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update user")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }


}
