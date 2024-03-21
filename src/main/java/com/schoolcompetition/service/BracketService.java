package com.schoolcompetition.service;

import com.schoolcompetition.model.dto.request.BracketRequest.CreateBracketRequest;
import com.schoolcompetition.model.dto.request.BracketRequest.UpdateBracketRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface BracketService {

    ResponseEntity<ResponseObj> getListBrackets(int page, int size);

    ResponseEntity<ResponseObj> getBracketById(int id);

    ResponseEntity<ResponseObj> getByName(String name);


    ResponseEntity<ResponseObj> createBracket(CreateBracketRequest createBracketRequest, BindingResult bindingResult);

    ResponseEntity<ResponseObj> updateBracket(int id, UpdateBracketRequest bracketRequest);
    ResponseEntity<ResponseObj> deleteBracket(int id);

    int countTotal();

}
