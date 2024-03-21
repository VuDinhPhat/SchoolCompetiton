package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<ResponseObj> getListCompetitions(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Competition> competitionPage = competitionRepository.findAll(pageable);

            if (competitionPage.hasContent()) {
                List<CompetitionResponse> competitionResponses = new ArrayList<>();

                for (Competition competition : competitionPage.getContent()) {
                    competitionResponses.add(CompetitionMapper.toCompetitionResponse(competition));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Competition successfully")
                        .data(competitionResponses)
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
                    .message("Failed to load Competition data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getCompetitionById(int id) {
        Competition competition = competitionRepository.getReferenceById(id);
        List<Competition> competitionList = competitionRepository.findAll();

        for (Competition c : competitionList) {
            if(c.equals(competition) && c.getStatus().equals(Status.ACTIVE)) {
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
            if (competition.getName().toLowerCase().contains(name.toLowerCase()) && competition.getStatus().equals(Status.ACTIVE)) {
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
            SchoolYear schoolYear = schoolYearRepository.findById(competitionRequest.getSchoolYearId()).orElse(null);
            if (schoolYear == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School year not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Competition competition = new Competition();
            competition.setName(competitionRequest.getName());
            competition.setDescription(competitionRequest.getDescription());
            competition.setHoldPlace(competitionRequest.getHoldPlace());
            competition.setSchoolYear(schoolYear);
            competition.setStatus(competitionRequest.getStatus());

            Competition savedCompetition = competitionRepository.save(competition);

            CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(savedCompetition);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Competition created successfully")
                    .data(competitionResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
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
            Competition competition = competitionRepository.findById(id).orElse(null);
            if (competition == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Competition not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (updateCompetitionRequest.getName() != null) {
                competition.setName(updateCompetitionRequest.getName());
            }
            if (updateCompetitionRequest.getDescription() != null) {
                competition.setDescription(updateCompetitionRequest.getDescription());
            }
            if (updateCompetitionRequest.getHoldPlace() != null) {
                competition.setHoldPlace(updateCompetitionRequest.getHoldPlace());
            }
            if (updateCompetitionRequest.getStatus() != null) {
                competition.setStatus(updateCompetitionRequest.getStatus());
            }
            if (updateCompetitionRequest.getSchoolYearId() != 0) {
                SchoolYear schoolYear = schoolYearRepository.findById(updateCompetitionRequest.getSchoolYearId()).orElse(null);
                if (schoolYear == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("School year not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                competition.setSchoolYear(schoolYear);
            }

            Competition updatedCompetition = competitionRepository.save(competition);

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
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update competition")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteCompetition(int id) {
        Competition competitionToDelete = competitionRepository.getReferenceById(id);
        List<Competition> competitionList = competitionRepository.findAll();

        for (Competition competition : competitionList) {
            if (competition.equals(competitionToDelete)) {
                competition.setStatus(Status.IN_ACTIVE);
                competitionRepository.save(competition);

                CompetitionResponse competitionResponse = CompetitionMapper.toCompetitionResponse(competitionToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Competition status changed to INACTIVE")
                        .data(competitionResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Competition not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }

    @Override
    public int countTotalCompetition() {
        List<Competition> competitionList = competitionRepository.findAll();
        return competitionList.size();
    }


}
