package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<ResponseObj> getAll();

    ResponseEntity<ResponseObj> getById(int id);

    ResponseEntity<ResponseObj> login(String email, String password);
}
