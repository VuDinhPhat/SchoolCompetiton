package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.ResultMapper;
import com.schoolcompetition.mapper.RoundMapper;
import com.schoolcompetition.model.dto.request.RoundRequest.CreateRoundRequest;
import com.schoolcompetition.model.dto.request.RoundRequest.UpdateRoundRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.ResultResponse;
import com.schoolcompetition.model.dto.response.RoundResponse;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Result;
import com.schoolcompetition.model.entity.Round;
import com.schoolcompetition.repository.CompetitionRepository;
import com.schoolcompetition.repository.RoundRepository;
import com.schoolcompetition.service.RoundService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoundServiceImpl implements RoundService {
    @Autowired
    RoundRepository roundRepository;
    @Autowired
    CompetitionRepository competitionRepository;

    @Override
    public ResponseEntity<ResponseObj> getListRounds(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Round> roundPage = roundRepository.findAll(pageable);

            if (roundPage.hasContent()) {
                List<RoundResponse> roundResponses = new ArrayList<>();

                for (Round round : roundPage.getContent()) {
                    roundResponses.add(RoundMapper.toRoundResponse(round));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Rounds successfully")
                        .data(roundResponses)
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
                    .message("Failed to load Rounds")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getRoundById(int id) {
        Round round = roundRepository.findById(id).orElse(null);

        if (round != null) {
            RoundResponse roundResponse = RoundMapper.toRoundResponse(round);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Result founded")
                    .data(roundResponse)
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
    public ResponseEntity<ResponseObj> getRoundByName(String name) {
        List<Round> roundList = roundRepository.findAll();
        List<RoundResponse> roundResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Round round : roundList) {
            if (round.getName().toLowerCase().contains(name.toLowerCase())) {
                roundResponses.add(RoundMapper.toRoundResponse(round));
            }
        }
        response.put("Rounds", roundResponses);

        if (!roundResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + roundResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createRound(CreateRoundRequest roundRequest) {
        try {
            Competition competition = competitionRepository.findById(roundRequest.getCompetitionId()).orElse(null);
            if (competition == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Competition not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Round round = new Round();
            round.setName(roundRequest.getName());
            round.setMap(roundRequest.getMap());
            round.setCompetition(competition);
            round.setStatus(roundRequest.getStatus());

            Round savedRound = roundRepository.save(round);

            RoundResponse roundResponse = RoundMapper.toRoundResponse(savedRound);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Round created successfully")
                    .data(roundResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create round")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateRound(int id, UpdateRoundRequest roundRequest) {
        try {
            Round round = roundRepository.findById(id).orElse(null);
            if (round == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Round not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (roundRequest.getName() != null) {
                round.setName(roundRequest.getName());
            }
            if (roundRequest.getMap() != null) {
                round.setMap(roundRequest.getMap());
            }
            if (roundRequest.getStatus() != null) {
                round.setStatus(roundRequest.getStatus());
            }
            if (roundRequest.getCompetitionId() != 0) {
                Competition competition = competitionRepository.findById(roundRequest.getCompetitionId()).orElse(null);
                if (competition == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Competition not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                round.setCompetition(competition);
            }

            Round updatedRound = roundRepository.save(round);

            RoundResponse roundResponse = RoundMapper.toRoundResponse(updatedRound);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Round updated successfully")
                    .data(roundResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update round")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteRound(int id) {
        Round roundToDelete = roundRepository.getReferenceById(id);
        List<Round> roundList = roundRepository.findAll();

        for (Round round : roundList) {
            if (round.equals(roundToDelete)) {
                round.setStatus(Status.IN_ACTIVE);
                roundRepository.save(round);

                RoundResponse roundResponse = RoundMapper.toRoundResponse(roundToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Round status changed to INACTIVE")
                        .data(roundResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }


        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Round not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }



}
