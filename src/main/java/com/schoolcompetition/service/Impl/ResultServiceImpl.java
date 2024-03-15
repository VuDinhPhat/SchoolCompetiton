package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.ResultMapper;
import com.schoolcompetition.model.dto.request.ResultRequest.CreateResultRequest;
import com.schoolcompetition.model.dto.request.ResultRequest.UpdateResultRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.ResultResponse;
import com.schoolcompetition.model.entity.Car;
import com.schoolcompetition.model.entity.Contestant;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.repository.CarRepository;
import com.schoolcompetition.repository.ContestantRepository;
import com.schoolcompetition.repository.MatchRepository;
import com.schoolcompetition.repository.ResultRepository;
import com.schoolcompetition.service.ResultService;
import jakarta.transaction.Transactional;
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
    @Autowired
    ContestantRepository contestantRepository;
    @Autowired
    CarRepository carRepository;
    @Autowired
    MatchRepository matchRepository;


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
        List<Result> resultList = resultRepository.findAll();

        for (Result r : resultList) {
            if (r.equals(result)) {
                ResultResponse resultResponse = ResultMapper.toResultResponse(result);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Result founded")
                        .data(resultResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("No record matching")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createResult(CreateResultRequest resultRequest) {
        try {
            // Kiểm tra xem contestantId, matchId và carId trong request có tồn tại không
            Contestant contestant = contestantRepository.findById(resultRequest.getContestantId()).orElse(null);
            if (contestant == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Contestant not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Match match = matchRepository.findById(resultRequest.getMatchId()).orElse(null);
            if (match == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Match not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Car car = carRepository.findById(resultRequest.getCarId()).orElse(null);
            if (car == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Car not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Tạo kết quả mới
            Result result = new Result();
            result.setScore(resultRequest.getScore());
            result.setFinishTime(resultRequest.getFinishTime());
            result.setContestant(contestant);
            result.setMatch(match);
            result.setCar(car);

            // Lưu kết quả mới vào cơ sở dữ liệu
            Result savedResult = resultRepository.save(result);

            // Chuyển đổi kết quả thành đối tượng response
            ResultResponse resultResponse = ResultMapper.toResultResponse(savedResult);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Result created successfully")
                    .data(resultResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create result")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateResult(int id, UpdateResultRequest resultRequest) {
        try {
            // Tìm kết quả cần cập nhật trong cơ sở dữ liệu
            Result result = resultRepository.findById(id).orElse(null);
            if (result == null) {
                // Trả về response not found nếu không tìm thấy kết quả
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Result not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Cập nhật thông tin kết quả nếu các trường không null
            if (resultRequest.getScore() != 0) {
                result.setScore(resultRequest.getScore());
            }
            if (resultRequest.getFinishTime() != null) {
                result.setFinishTime(resultRequest.getFinishTime());
            }
            if (resultRequest.getContestantId() != 0) {
                Contestant contestant = contestantRepository.findById(resultRequest.getContestantId()).orElse(null);
                if (contestant == null) {
                    // Trả về response not found nếu không tìm thấy thí sinh
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Contestant not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                result.setContestant(contestant);
            }
            if (resultRequest.getMatchId() != 0) {
                Match match = matchRepository.findById(resultRequest.getMatchId()).orElse(null);
                if (match == null) {
                    // Trả về response not found nếu không tìm thấy trận đấu
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Match not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                result.setMatch(match);
            }
            if (resultRequest.getCarId() != 0) {
                Car car = carRepository.findById(resultRequest.getCarId()).orElse(null);
                if (car == null) {
                    // Trả về response not found nếu không tìm thấy xe
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Car not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                result.setCar(car);
            }

            // Lưu kết quả đã cập nhật vào cơ sở dữ liệu
            Result updatedResult = resultRepository.save(result);

            // Chuyển đổi kết quả đã cập nhật thành đối tượng response
            ResultResponse resultResponse = ResultMapper.toResultResponse(updatedResult);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Result updated successfully")
                    .data(resultResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update result")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

}
