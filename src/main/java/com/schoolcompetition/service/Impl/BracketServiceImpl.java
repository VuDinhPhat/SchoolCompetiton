package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
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

import com.schoolcompetition.util.ValidatorUtil;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Service
public class BracketServiceImpl implements BracketService {
    @Autowired
    BracketRepository bracketRepository;
    @Autowired
    RoundRepository roundRepository;


    @Override
    public ResponseEntity<ResponseObj> getListBrackets(int page, int size) {
        try {
            // Tạo đối tượng Pageable để xác định trang và kích thước trang
            Pageable pageable = PageRequest.of(page, size);

            // Truy vấn dữ liệu Bracket từ cơ sở dữ liệu sử dụng phân trang
            Page<Bracket> bracketPage = bracketRepository.findAll(pageable);

            // Kiểm tra xem trang có dữ liệu không
            if (bracketPage.hasContent()) {
                List<BracketResponse> bracketResponses = new ArrayList<>();

                // Chuyển đổi danh sách Bracket thành danh sách BracketResponse
                for (Bracket bracket : bracketPage.getContent()) {
                    bracketResponses.add(BrackerMapper.toBracketResponse(bracket));
                }

                // Tạo đối tượng ResponseObj chứa danh sách BracketResponse
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Bracket successfully")
                        .data(bracketResponses)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            } else {
                // Trả về thông báo rằng không có dữ liệu nào được tìm thấy trên trang cụ thể
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("No data found on page " + page)
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về thông báo lỗi nếu có vấn đề xảy ra khi lấy dữ liệu
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message("Failed to load Bracket data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> getBracketById(int id) {
        Bracket bracket = bracketRepository.getReferenceById(id);
        List<Bracket> bracketList = bracketRepository.findAll();

        for (Bracket bracket1 : bracketList) {
            if (bracket1.equals(bracket)) {
                BracketResponse bracketResponse = BrackerMapper.toBracketResponse(bracket);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Bracket founded")
                        .data(bracketResponse)
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
    public ResponseEntity<ResponseObj> createBracket(CreateBracketRequest createBracketRequest, BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();

        Round currentRound = roundRepository.findById(createBracketRequest.getRoundId()).orElse(null);
        if (currentRound == null) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Invalid Round")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }

        Bracket bracket = new Bracket();
        bracket.setName(createBracketRequest.getName());
        bracket.setRound(currentRound);
        bracket.setStatus(createBracketRequest.getStatus());
        response.put("Bracket", bracket);
        bracketRepository.save(bracket);

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.OK))
                .message("Bracket created successfully")
                .data(response)
                .build();
        return ResponseEntity.ok().body(responseObj);
    }


    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateBracket(int id, UpdateBracketRequest bracketRequest) {
        try {
            Bracket bracket = bracketRepository.findById(id).orElse(null);
            if (bracket == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Bracket not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (bracketRequest.getName() != null) {
                bracket.setName(bracketRequest.getName());
            }
            if (bracketRequest.getStatus() != null) {
                bracket.setStatus(bracketRequest.getStatus());
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

            Bracket updatedBracket = bracketRepository.save(bracket);

            BracketResponse bracketResponse = BrackerMapper.toBracketResponse(updatedBracket);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Bracket updated successfully")
                    .data(bracketResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update bracket")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteBracket(int id) {
        Bracket bracketToDelete = bracketRepository.getReferenceById(id);
        List<Bracket> bracketList = bracketRepository.findAll();

        for (Bracket bracket : bracketList) {
            if (bracket.equals(bracketToDelete)) {
                bracket.setStatus(Status.IN_ACTIVE);
                bracketRepository.save(bracket);

                BracketResponse bracketResponse = BrackerMapper.toBracketResponse(bracketToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Bracket status changed to INACTIVE")
                        .data(bracketResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Bracket not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }



}
