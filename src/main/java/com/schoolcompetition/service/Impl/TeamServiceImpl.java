package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.StudentMapper;
import com.schoolcompetition.mapper.TeamMapper;
import com.schoolcompetition.model.dto.request.TeamRequest.CreateTeamRequest;
import com.schoolcompetition.model.dto.request.TeamRequest.UpdateTeamRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.StudentResponse;
import com.schoolcompetition.model.dto.response.TeamResponse;
import com.schoolcompetition.model.entity.Coach;
import com.schoolcompetition.model.entity.Competition;
import com.schoolcompetition.model.entity.Student;
import com.schoolcompetition.model.entity.Team;
import com.schoolcompetition.repository.CoachRepository;
import com.schoolcompetition.repository.CompetitionRepository;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.repository.TeamRepository;
import com.schoolcompetition.service.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    CoachRepository coachRepository;
    @Autowired
    CompetitionRepository competitionRepository;


    @Override
    public ResponseEntity<ResponseObj> getListTeams(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<Team> teamPage = teamRepository.findAll(pageable);

            if (teamPage.hasContent()) {
                List<TeamResponse> teamResponses = new ArrayList<>();

                for (Team team : teamPage.getContent()) {
                    teamResponses.add(TeamMapper.toTeamResponse(team));
                }

                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Teams successfully")
                        .data(teamResponses)
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
                    .message("Failed to load Teams")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getTeamById(int id) {
        Team team = teamRepository.findById(id).orElse(null);

        if (team != null && team.getStatus().equals(Status.ACTIVE)) {
            TeamResponse teamResponse = TeamMapper.toTeamResponse(team);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Team founded")
                    .data(teamResponse)
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
    public ResponseEntity<ResponseObj> getTeamByName(String name) {
        List<Team> teamList = teamRepository.findAll();
        List<TeamResponse> teamResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (Team team : teamList) {
            if (team.getName().toLowerCase().contains(name.toLowerCase()) && team.getStatus().equals(Status.ACTIVE)) {
                teamResponses.add(TeamMapper.toTeamResponse(team));
            }
        }
        response.put("Teams", teamResponses);

        if (!teamResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + teamResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createTeam(CreateTeamRequest teamRequest) {
        try {
            Coach coach = coachRepository.findById(teamRequest.getCoachId()).orElse(null);
            if (coach == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Coach not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Competition competition = competitionRepository.findById(teamRequest.getCompetitionId()).orElse(null);
            if (competition == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Competition not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            Team team = new Team();
            team.setName(teamRequest.getName());
            team.setCoach(coach);
            team.setCompetition(competition);
            team.setStatus(teamRequest.getStatus());

            Team savedTeam = teamRepository.save(team);

            TeamResponse teamResponse = TeamMapper.toTeamResponse(savedTeam);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Team created successfully")
                    .data(teamResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create team")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseObj> updateTeam(int id, UpdateTeamRequest teamRequest) {
        try {
            Team team = teamRepository.findById(id).orElse(null);
            if (team == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("Team not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (teamRequest.getName() != null) {
                team.setName(teamRequest.getName());
            }
            if (teamRequest.getStatus() != null) {
                team.setStatus(teamRequest.getStatus());
            }
            if (teamRequest.getCoachId() != 0) {
                Coach coach = coachRepository.findById(teamRequest.getCoachId()).orElse(null);
                if (coach == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Coach not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                team.setCoach(coach);
            }
            if (teamRequest.getCompetitionId() != 0) {
                Competition competition = competitionRepository.findById(teamRequest.getCompetitionId()).orElse(null);
                if (competition == null) {
                    ResponseObj responseObj = ResponseObj.builder()
                            .status(String.valueOf(HttpStatus.NOT_FOUND))
                            .message("Competition not found")
                            .data(null)
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
                }
                team.setCompetition(competition);
            }

            Team updatedTeam = teamRepository.save(team);

            TeamResponse teamResponse = TeamMapper.toTeamResponse(updatedTeam);

            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("Team updated successfully")
                    .data(teamResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update team")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteTeam(int id) {
        Team teamToDelete = teamRepository.getReferenceById(id);
        List<Team> teamList = teamRepository.findAll();

        for (Team team : teamList) {
            if (team.equals(teamToDelete)) {
                team.setStatus(Status.IN_ACTIVE);
                teamRepository.save(team);

                TeamResponse teamResponse = TeamMapper.toTeamResponse(teamToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Team status changed to INACTIVE")
                        .data(teamResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("Team not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }

    @Override
    public int countTotalTeam() {
        List<Team> teamList = teamRepository.findAll();
        return teamList.size();
    }


}
