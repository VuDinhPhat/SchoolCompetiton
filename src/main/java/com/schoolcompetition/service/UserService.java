package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.UserRequest.CreateUserRequest;
import com.schoolcompetition.model.dto.request.UserRequest.UpdateUserRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<ResponseObj> getListUsers(int page, int size);

    ResponseEntity<ResponseObj> getById(int id);

    ResponseEntity<ResponseObj> login(String email, String password);
    ResponseEntity<ResponseObj> deleteUser(int id);
    ResponseEntity<ResponseObj> updateUser(int id, UpdateUserRequest userRequest);
    ResponseEntity<ResponseObj> createUser(CreateUserRequest userRequest);
    int countTotalUser();

}
