package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.MatchMapper;
import com.schoolcompetition.model.dto.request.MatchRequest.CreateMatchRequest;
import com.schoolcompetition.model.dto.request.MatchRequest.UpdateMatchRequest;
import com.schoolcompetition.model.dto.response.MatchResponse;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.entity.Bracket;
import com.schoolcompetition.model.entity.Match;
import com.schoolcompetition.repository.BracketRepository;
import com.schoolcompetition.repository.MatchRepository;
import com.schoolcompetition.service.MatchService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    BracketRepository bracketRepository;

    @Override
    public ResponseEntity<ResponseObj> getListMatches(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Match> matchPage = matchRepository.findAll(pageable);

            if (matchPage.hasContent()) {
                List<MatchResponse> matchResponses = new ArrayList<>();

                for (Match match : matchPage.getContent()) {
                    matchResponses.add(MatchMapper.toMatchResponse(match));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Match successfully")
                        .data(matchResponses)
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
                    .message("Failed to load Match data")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getById(int id) {
        Match match = matchRepository.getReferenceById(id);
        List<Match> matchList = matchRepository.findAll();

        for (Match m : matchList) {
            if (m.equals(match)) {
                MatchResponse matchResponse = MatchMapper.toMatchResponse(match);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Match founded")
                        .data(matchResponse)
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
    public ResponseEntity<ResponseObj> getMatchByName(String name) {
        List<Match> matchList = matchRepository.findAll();
        List<MatchResponse> matchResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Match match : matchList) {
            if (match.getName().toLowerCase().contains(name.toLowerCase())) {
                matchResponses.add(MatchMapper.toMatchResponse(match));
            }
        }
        response.put("Matches", matchResponses);

        if (!matchResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + matchResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createMatch(CreateMatchRequest createMatchRequest) {
        try {
            Bracket bracket = bracketRepository.findById(createMatchRequest.getBracketId()).orElse(null);
            if (bracket == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Bracket not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Match match = new Match();
            match.setName(createMatchRequest.getName());
            match.setStartTime(createMatchRequest.getStartTime());
            match.setPlace(createMatchRequest.getPlace());
            match.setLap(createMatchRequest.getLap());
            match.setBracket(bracket);
            match.setStatus(createMatchRequest.getStatus());

            Match savedMatch = matchRepository.save(match);

            MatchResponse matchResponse = MatchMapper.toMatchResponse(savedMatch);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Match created successfully")
                    .data(matchResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create match")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateMatch(int id, UpdateMatchRequest updateMatchRequest) {
        try {
            Match match = matchRepository.findById(id).orElse(null);
            if (match == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Match not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (updateMatchRequest.getName() != null) {
                match.setName(updateMatchRequest.getName());
            }
            if (updateMatchRequest.getStartTime() != null) {
                match.setStartTime(updateMatchRequest.getStartTime());
            }
            if (updateMatchRequest.getPlace() != null) {
                match.setPlace(updateMatchRequest.getPlace());
            }
            if (updateMatchRequest.getStatus() != null) {
                match.setStatus(updateMatchRequest.getStatus());
            }
            if (updateMatchRequest.getLap() != 0) {
                match.setLap(updateMatchRequest.getLap());
            }
            if (updateMatchRequest.getBracketId() != 0) {
                Bracket bracket = bracketRepository.findById(updateMatchRequest.getBracketId()).orElse(null);
                if (bracket == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Bracket not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                match.setBracket(bracket);
            }

            Match updatedMatch = matchRepository.save(match);

            MatchResponse matchResponse = MatchMapper.toMatchResponse(updatedMatch);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Match updated successfully")
                    .data(matchResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update match")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteMatch(int id) {
        Match matchToDelete = matchRepository.getReferenceById(id);
        List<Match> matchList = matchRepository.findAll();

        for (Match match : matchList) {
            if (match.equals(matchToDelete)) {
                match.setStatus(Status.IN_ACTIVE);
                matchRepository.save(match);

                MatchResponse matchResponse = MatchMapper.toMatchResponse(matchToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Match status changed to INACTIVE")
                        .data(matchResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Match not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }



}
