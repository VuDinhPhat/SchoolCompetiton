package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.ResultMapper;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.ResultResponse;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.repository.ResultRepository;
import com.schoolcompetition.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository resultRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllResult() {
        List<Result> resultList = resultRepository.findAll();
        List<ResultResponse> resultResponses = new ArrayList<>();

        for (Result result : resultList) {
            resultResponses.add(ResultMapper.toResultResponse(result));
        }

        if (!resultResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Results successfully")
                    .data(resultResponses)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Results failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getResultById(int id) {
        Result result = resultRepository.findById(id).orElse(null);

        if (result != null) {
            ResultResponse resultResponse = ResultMapper.toResultResponse(result);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Result founded")
                    .data(resultResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }
}
