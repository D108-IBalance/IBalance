package com.ssafy.ibalance.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record WrittenDiaryResponse(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        String content,
        String mealTime,

        List<DiaryMenuResponse> diaryMenuList,

        List<DietMaterialResponse> materials
) {
}
