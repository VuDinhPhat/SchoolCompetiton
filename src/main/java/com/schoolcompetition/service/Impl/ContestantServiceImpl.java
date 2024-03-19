package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.BrackerMapper;
import com.schoolcompetition.mapper.ContestantMapper;
import com.schoolcompetition.model.dto.request.ContestantRequest.CreateContestantRequest;
import com.schoolcompetition.model.dto.request.ContestantRequest.UpdateContestantRequest;
import com.schoolcompetition.model.dto.response.BracketResponse;
import com.schoolcompetition.model.dto.response.ContestantResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.*;
import com.schoolcompetition.repository.CoachRepository;
import com.schoolcompetition.repository.ContestantRepository;
import com.schoolcompetition.repository.StudentRepository;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.ContestantService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContestantServiceImpl implements ContestantService {
    @Autowired
    ContestantRepository contestantRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    TeamRepository teamRepository;
    @Override
    public ResponseEntity<ResponseObj> getListContestants(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Contestant> contestantPage = contestantRepository.findAll(pageable);

            if (contestantPage.hasContent()) {
                List<ContestantResponse> contestantResponses = new ArrayList<>();

                for (Contestant contestant : contestantPage.getContent()) {
                    contestantResponses.add(ContestantMapper.toContestantResponse(contestant));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Contestant successfully")
                        .data(contestantResponses)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            } else {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("No data found on page " + page)
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR))
                    .message("Failed to load Contestant data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getById(int id) {
        Contestant contestant = contestantRepository.getReferenceById(id);
        List<Contestant> contestantList = contestantRepository.findAll();

        for (Contestant c : contestantList) {
            if (c.equals(contestant)) {
                ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(contestant);

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Bracket founded")
                        .data(contestantResponse)
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
    public ResponseEntity<ResponseObj> createContestant(CreateContestantRequest contestantRequest) {
        try {
            Student student = studentRepository.findById(contestantRequest.getStudentId()).orElse(null);
            if (student == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Student not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Coach coach = coachRepository.findById(contestantRequest.getCoachId()).orElse(null);
            if (coach == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Coach not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Team team = teamRepository.findById(contestantRequest.getTeamId()).orElse(null);
            if (team == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Team not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Contestant contestant = new Contestant();
            contestant.setStudent(student);
            contestant.setCoach(coach);
            contestant.setTeam(team);
            contestant.setStatus(contestantRequest.getStatus());

            Contestant savedContestant = contestantRepository.save(contestant);

            ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(savedContestant);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Contestant created successfully")
                    .data(contestantResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create contestant")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateContestant(int id, UpdateContestantRequest contestantRequest) {
        try {
            Contestant contestant = contestantRepository.findById(id).orElse(null);
            if (contestant == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Contestant not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (contestantRequest.getStudentId() != 0) {
                Student student = studentRepository.findById(contestantRequest.getStudentId()).orElse(null);
                if (student == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Student not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                contestant.setStudent(student);
            }
            if (contestantRequest.getCoachId() != 0) {
                Coach coach = coachRepository.findById(contestantRequest.getCoachId()).orElse(null);
                if (coach == null) {
                    // Trả về response not found nếu không tìm thấy huấn luyện viên
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Coach not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                contestant.setCoach(coach);
            }
            if (contestantRequest.getTeamId() != 0) {
                Team team = teamRepository.findById(contestantRequest.getTeamId()).orElse(null);
                if (team == null) {
                    // Trả về response not found nếu không tìm thấy đội
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Team not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                contestant.setTeam(team);
            }
            if (contestantRequest.getStatus() != null) {
                contestant.setStatus(contestantRequest.getStatus());
            }

            // Lưu thí sinh đã cập nhật vào cơ sở dữ liệu
            Contestant updatedContestant = contestantRepository.save(contestant);

            // Chuyển đổi thí sinh đã cập nhật thành đối tượng response
            ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(updatedContestant);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Contestant updated successfully")
                    .data(contestantResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update contestant")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteContestant(int id) {
        Contestant contestantToDelete = contestantRepository.getReferenceById(id);
        List<Contestant> contestantList = contestantRepository.findAll();

        for (Contestant contestant : contestantList) {
            if (contestant.equals(contestantToDelete)) {
                contestant.setStatus(Status.IN_ACTIVE); // Chuyển trạng thái thành INACTIVE
                contestantRepository.save(contestant); // Lưu lại thay đổi vào cơ sở dữ liệu

                ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(contestantToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Contestant status changed to INACTIVE")
                        .data(contestantResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        // Nếu không tìm thấy contestant
        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Contestant not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }


}
