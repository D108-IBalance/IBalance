package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.type.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class ChildDetailResponse {
    private Integer childId;
    private String imageUrl;
    private String name;
    private LocalDate birthDate;
    private Gender gender;
    private Double height;
    private Double weight;
    private LocalDate lastUpdateDate;
}
