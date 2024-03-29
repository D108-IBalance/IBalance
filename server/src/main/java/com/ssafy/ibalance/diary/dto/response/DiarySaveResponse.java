package com.ssafy.ibalance.diary.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DiarySaveResponse(

        Long dietId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date,
        String content,
        List<DietMaterialResponse> materials
) {
}
