package com.schoolcompetition.service.Impl;

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
    public ResponseEntity<ResponseObj> getAll() {
        List<Contestant> contestantList = contestantRepository.findAll();
        List<ContestantResponse> contestantResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Contestant contestant : contestantList) {
            contestantResponses.add(ContestantMapper.toContestantResponse(contestant));
        }
        response.put("Contestant", contestantResponses);

        if (!contestantResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("Load all Contestant successfully")
                    .data(response)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.BAD_REQUEST))
                .message("Load all Contestant failed")
                .data(null)
                .build();
        return ResponseEntity.badRequest().body(responseObj);

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
            // Kiểm tra xem studentId, coachId và teamId trong request có tồn tại không
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

            // Tạo thí sinh mới
            Contestant contestant = new Contestant();
            contestant.setStudent(student);
            contestant.setCoach(coach);
            contestant.setTeam(team);

            // Lưu thí sinh mới vào cơ sở dữ liệu
            Contestant savedContestant = contestantRepository.save(contestant);

            // Chuyển đổi thí sinh thành đối tượng response
            ContestantResponse contestantResponse = ContestantMapper.toContestantResponse(savedContestant);

            // Tạo và trả về response thành công
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Contestant created successfully")
                    .data(contestantResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về response thất bại nếu có lỗi xảy ra
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
            // Tìm thí sinh cần cập nhật trong cơ sở dữ liệu
            Contestant contestant = contestantRepository.findById(id).orElse(null);
            if (contestant == null) {
                // Trả về response not found nếu không tìm thấy thí sinh
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Contestant not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            // Cập nhật thông tin thí sinh nếu các trường không null
            if (contestantRequest.getStudentId() != 0) {
                Student student = studentRepository.findById(contestantRequest.getStudentId()).orElse(null);
                if (student == null) {
                    // Trả về response not found nếu không tìm thấy sinh viên
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

}
