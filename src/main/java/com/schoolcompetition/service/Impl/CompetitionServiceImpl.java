package com.schoolcompetition.service.Impl;

import com.schoolcompetition.mapper.CompetitionMapper;
import com.schoolcompetition.model.dto.request.CompetitionRequest.CreateCompetitionRequest;
import com.schoolcompetition.model.dto.request.CompetitionRequest.UpdateCompetitionRequest;
import com.schoolcompetition.model.dto.response.CompetitionResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.SchoolYear;
import com.schoolcompetition.repository.CompetitionRepository;
import com.schoolcompetition.repository.SchoolYearRepository;
import com.schoolcompetition.service.CompetitionService;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionServiceImpl implements CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;
    @Autowired
    SchoolYearRepository schoolYearRepository;

    @Override
    public ResponseEntity<ResponseObj> getAllCompetition(){
        List<Competition> competitionList = competitionRepository.findAll();
        List<CompetitionResponse> competitionResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Competition competition : competitionList) {
            competitionResponses.add(CompetitionMapper.toCompetitionResponse(competition));
        }
        response.put("Competition", competitionResponses);

        if (!competitionResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Load all Competition successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Competition failed")
                .data(null)
                .build();
        return ResponseEntity.ok().body(responseObj);

    }

    @Override
    public ResponseEntity<ResponseObj> getCompetitionById(int id) {
        Competition competition = competitionRepository.getReferenceById(id);
        List<Competition> competitionList = competitionRepository.findAll();

        for (Competition c : competitionList) {
            if(c.equals(competition)) {
                CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(competition);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Bracket founded")
                        .data(competitionResponse)
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
    public ResponseEntity<ResponseObj> getCompetitionByName(String name) {
        List<Competition> competitionList = competitionRepository.findAll();
        List<CompetitionResponse> competitionResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Competition competition : competitionList) {
            if (competition.getName().toLowerCase().contains(name.toLowerCase())) {
                competitionResponses.add(CompetitionMapper.toCompetitionResponse(competition));
            }
        }
        response.put("Competitions", competitionResponses);

        if (!competitionResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + competitionResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createCompetition(CreateCompetitionRequest competitionRequest) {
        try {
            // Kiểm tra xem schoolYearId trong request có tồn tại không
            SchoolYear schoolYear = schoolYearRepository.findById(competitionRequest.getSchoolYearId()).orElse(null);
            if (schoolYear == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School year not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Tạo cuộc thi mới
            Competition competition = new Competition();
            competition.setName(competitionRequest.getName());
            competition.setDescription(competitionRequest.getDescription());
            competition.setHoldPlace(competitionRequest.getHoldPlace());
            competition.setSchoolYear(schoolYear);

            // Lưu cuộc thi mới vào cơ sở dữ liệu
            Competition savedCompetition = competitionRepository.save(competition);

            // Chuyển đổi cuộc thi thành đối tượng response
            CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(savedCompetition);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Competition created successfully")
                    .data(competitionResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create competition")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateCompetition(int id, UpdateCompetitionRequest updateCompetitionRequest) {
        try {
            // Tìm cuộc thi cần cập nhật trong cơ sở dữ liệu
            Competition competition = competitionRepository.findById(id).orElse(null);
            if (competition == null) {
                // Trả về response not found nếu không tìm thấy cuộc thi
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Competition not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Cập nhật thông tin cuộc thi nếu các trường không null
            if (updateCompetitionRequest.getName() != null) {
                competition.setName(updateCompetitionRequest.getName());
            }
            if (updateCompetitionRequest.getDescription() != null) {
                competition.setDescription(updateCompetitionRequest.getDescription());
            }
            if (updateCompetitionRequest.getHoldPlace() != null) {
                competition.setHoldPlace(updateCompetitionRequest.getHoldPlace());
            }
            if (updateCompetitionRequest.getSchoolYearId() != 0) {
                SchoolYear schoolYear = schoolYearRepository.findById(updateCompetitionRequest.getSchoolYearId()).orElse(null);
                if (schoolYear == null) {
                    // Trả về response not found nếu không tìm thấy năm học
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("School year not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                competition.setSchoolYear(schoolYear);
            }

            // Lưu cuộc thi đã cập nhật vào cơ sở dữ liệu
            Competition updatedCompetition = competitionRepository.save(competition);

            // Chuyển đổi cuộc thi đã cập nhật thành đối tượng response
            CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(updatedCompetition);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Competition updated successfully")
                    .data(competitionResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update competition")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }


}
