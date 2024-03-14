package com.schoolcompetition.model.dto.request.MatchRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateMatchRequest {
    String name;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    Date startTime;

    String place;

    int lap;

    Integer bracketId; // Integer để cho phép giá trị null khi không cần thay đổi bracketId
}
