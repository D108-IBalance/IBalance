package com.ssafy.ibalance.diet.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class DietByDateResponse {
    private Long dietId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dietDate;

    private Integer sequence;
    private List<DietMenuResponse> dietMenuList;
}