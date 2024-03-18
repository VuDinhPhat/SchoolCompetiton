package com.schoolcompetition.model.dto.request.StudentRequest;

import com.schoolcompetition.enums.Gender;
import com.schoolcompetition.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStudentRequest {
    String name;
    Date dob;
    Gender sex;
    Status status;
    int schoolId;
}
