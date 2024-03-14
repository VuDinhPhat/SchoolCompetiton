package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.response.ResponseObj;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface BracketService {

    ResponseEntity<ResponseObj> getAllBracket();

    ResponseEntity<ResponseObj> getBracketById(int id);

    ResponseEntity<ResponseObj> getByName(String name);
}
