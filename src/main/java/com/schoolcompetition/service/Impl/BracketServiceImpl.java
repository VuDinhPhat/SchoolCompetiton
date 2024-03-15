package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.BrackerMapper;
import com.schoolcompetition.model.dto.request.BracketRequest.CreateBracketRequest;
import com.schoolcompetition.model.dto.request.BracketRequest.UpdateBracketRequest;
import com.schoolcompetition.model.dto.response.BracketResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.repository.BracketRepository;
import com.schoolcompetition.repository.RoundRepository;
import com.schoolcompetition.service.BracketService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BracketServiceImpl implements BracketService {
    @Autowired
    BracketRepository bracketRepository;
    @Autowired
    RoundRepository roundRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllBracket() {
        List<Bracket> bracketList = bracketRepository.findAll();
        List<BracketResponse> bracketResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Bracket bracket : bracketList) {
            bracketResponses.add(BrackerMapper.toBracketResponse(bracket));
        }
        response.put("Bracket", bracketResponses);

        if (!bracketResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Bracket successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Bracket failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);
    }

    @Override
    public ResponseEntity<ResponseObj> getBracketById(int id) {
        Bracket bracket = bracketRepository.getReferenceById(id);

        if (bracket != null) {
            BracketResponse bracketResponse = BrackerMapper.toBracketResponse(bracket);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket founded")
                    .data(bracketResponse)
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

    @Override
    public ResponseEntity<ResponseObj> getByName(String name) {
        List<Bracket> bracketList = bracketRepository.findAll();
        List<BracketResponse> bracketResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Bracket bracket : bracketList) {
            if (bracket.getName().toLowerCase().contains(name.toLowerCase())) {
                bracketResponses.add(BrackerMapper.toBracketResponse(bracket));
            }
        }
        response.put("Bracket", bracketResponses);

        if (!bracketResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + bracketResponses.size() + " record matching")
                    .data(response)
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

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> createBracket(CreateBracketRequest bracketRequest) {
        try {
            // Kiểm tra xem roundId trong request có tồn tại không
            Round round = roundRepository.findById(bracketRequest.getRoundId()).orElse(null);
            if (round == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Round not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Tạo bracket mới
            Bracket bracket = new Bracket();
            bracket.setName(bracketRequest.getName());
            bracket.setRound(round);

            // Lưu bracket mới vào cơ sở dữ liệu
            Bracket savedBracket = bracketRepository.save(bracket);

            // Chuyển đổi bracket thành đối tượng response
            BracketResponse bracketResponse = BrackerMapper.toBracketResponse(savedBracket);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket created successfully")
                    .data(bracketResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create bracket")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateBracket(int id, UpdateBracketRequest bracketRequest) {
        try {
            // Tìm bracket cần cập nhật trong cơ sở dữ liệu
            Bracket bracket = bracketRepository.findById(id).orElse(null);
            if (bracket == null) {
                // Trả về response not found nếu không tìm thấy bracket
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Bracket not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Cập nhật thông tin bracket nếu các trường không null
            if (bracketRequest.getName() != null) {
                bracket.setName(bracketRequest.getName());
            }
            if (bracketRequest.getRoundId() != 0) {
                Round round = roundRepository.findById(bracketRequest.getRoundId()).orElse(null);
                if (round == null) {
                    // Trả về response not found nếu không tìm thấy round
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Round not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                bracket.setRound(round);
            }

            // Lưu bracket đã cập nhật vào cơ sở dữ liệu
            Bracket updatedBracket = bracketRepository.save(bracket);

            // Chuyển đổi bracket đã cập nhật thành đối tượng response
            BracketResponse bracketResponse = BrackerMapper.toBracketResponse(updatedBracket);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket updated successfully")
                    .data(bracketResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update bracket")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

}
