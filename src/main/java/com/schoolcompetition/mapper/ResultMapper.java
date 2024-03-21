package com.schoolcompetition.mapper;

import com.schoolcompetition.model.dto.response.ResultResponse;
import com.schoolcompetition.model.entity.Result;

public class ResultMapper {
    public static ResultResponse toResultResponse(Result result) {
        return ResultResponse.builder()
                .id(result.getId())
                .score(result.getScore())
                .finishTime(result.getFinishTime())
                .contestant(result.getContestant())
                .match(result.getMatch())
                .status(result.getStatus())
                .car(result.getCar())
                .build();
    }
}
