package com.ssafy.ibalance.child.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ibalance.child.entity.Growth;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private Gender gender;
    private Double height;
    private Double weight;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastUpdateDate;

    public static ChildDetailResponse convertEntityToDto(Growth growth) {
        return ChildDetailResponse.builder()
                .childId(growth.getChild().getId())
                .imageUrl(growth.getChild().getImageUrl())
                .name(growth.getChild().getName())
                .birthDate(growth.getChild().getBirthDate())
                .gender(growth.getChild().getGender())
                .height(growth.getHeight())
                .weight(growth.getWeight())
                .lastUpdateDate(growth.getCreatedTime().toLocalDate())
                .build();
    }
}
