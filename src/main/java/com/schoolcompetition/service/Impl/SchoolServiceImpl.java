package com.schoolcompetition.service.Impl;

import com.schoolcompetition.enums.Status;
import com.schoolcompetition.mapper.SchoolMapper;
import com.schoolcompetition.model.dto.request.SchoolRequest.CreateSchoolRequest;
import com.schoolcompetition.model.dto.request.SchoolRequest.UpdateSchoolRequest;
import com.schoolcompetition.model.dto.response.ResponseObj;
import com.schoolcompetition.model.dto.response.SchoolResponse;
import com.schoolcompetition.model.entity.School;
import com.schoolcompetition.repository.SchoolRepository;
import com.schoolcompetition.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    SchoolRepository schoolRepository;

    @Override
    public ResponseEntity<ResponseObj> getListSchools(int page, int size) {
        try {
            // Tạo đối tượng Pageable để xác định trang và kích thước trang
            Pageable pageable = PageRequest.of(page, size);

            // Truy vấn dữ liệu School từ cơ sở dữ liệu sử dụng phân trang
            Page<School> schoolPage = schoolRepository.findAll(pageable);

            // Kiểm tra xem trang có dữ liệu không
            if (schoolPage.hasContent()) {
                List<SchoolResponse> schoolResponses = new ArrayList<>();

                // Chuyển đổi danh sách School thành danh sách SchoolResponse
                for (School school : schoolPage.getContent()) {
                    schoolResponses.add(SchoolMapper.toSchoolResponse(school));
                }

                // Tạo đối tượng ResponseObj chứa danh sách SchoolResponse
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("Load all Schools successfully")
                        .data(schoolResponses)
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
                    .message("Failed to load Schools")
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObj);
        }
    }


    @Override
    public ResponseEntity<ResponseObj> getSchoolById(int id) {
        School school = schoolRepository.findById(id).orElse(null);

        if (school != null) {
            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(school);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("School founded")
                    .data(schoolResponse)
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
    public ResponseEntity<ResponseObj> getSchoolByName(String name) {
        List<School> schoolList = schoolRepository.findAll();
        List<SchoolResponse> schoolResponses = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for (School school : schoolList) {
            if (school.getName().toLowerCase().contains(name.toLowerCase())) {
                schoolResponses.add(SchoolMapper.toSchoolResponse(school));
            }
        }
        response.put("Schools", schoolResponses);

        if (!schoolResponses.isEmpty()) {
            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("There are " + schoolResponses.size() + " record(s) matching")
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
    public ResponseEntity<ResponseObj> createSchool(CreateSchoolRequest requestSchool) {
        try {
            School newSchool = new School();
            newSchool.setName(requestSchool.getName());
            newSchool.setAddress(requestSchool.getAddress());
            newSchool.setStatus(requestSchool.getStatus());

            School savedSchool = schoolRepository.save(newSchool);

            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(savedSchool);

            ResponseObj responseObj = ResponseObj.builder()
                    .status("OK")
                    .message("School created successfully")
                    .data(schoolResponse)
                    .build();

            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to create school")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> updateSchool(int id, UpdateSchoolRequest requestSchool) {
        try {
            School school = schoolRepository.findById(id).orElse(null);
            if (school == null) {
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.NOT_FOUND))
                        .message("School not found")
                        .data(null)
                        .build();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
            }

            if (requestSchool.getName() != null) {
                school.setName(requestSchool.getName());
            }

            if (requestSchool.getAddress() != null) {
                school.setAddress(requestSchool.getAddress());
            }
            if (requestSchool.getStatus() != null) {
                school.setStatus(requestSchool.getStatus());
            }

            School updatedSchool = schoolRepository.save(school);

            SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(updatedSchool);
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.OK))
                    .message("School updated successfully")
                    .data(schoolResponse)
                    .build();
            return ResponseEntity.ok().body(responseObj);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseObj responseObj = ResponseObj.builder()
                    .status(String.valueOf(HttpStatus.BAD_REQUEST))
                    .message("Failed to update school")
                    .data(null)
                    .build();
            return ResponseEntity.badRequest().body(responseObj);
        }
    }

    @Override
    public ResponseEntity<ResponseObj> deleteSchool(int id) {
        School schoolToDelete = schoolRepository.getReferenceById(id);
        List<School> schoolList = schoolRepository.findAll();

        for (School school : schoolList) {
            if (school.equals(schoolToDelete)) {
                school.setStatus(Status.IN_ACTIVE);
                schoolRepository.save(school);

                SchoolResponse schoolResponse = SchoolMapper.toSchoolResponse(schoolToDelete);
                ResponseObj responseObj = ResponseObj.builder()
                        .status(String.valueOf(HttpStatus.OK))
                        .message("School status changed to INACTIVE")
                        .data(schoolResponse)
                        .build();
                return ResponseEntity.ok().body(responseObj);
            }
        }

        ResponseObj responseObj = ResponseObj.builder()
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .message("School not found")
                .data(null)
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObj);
    }

}
