package com.ssafy.ibalance.child.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssafy.ibalance.child.dto.ChildDetailDto;
import com.ssafy.ibalance.child.type.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

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
    private List<Integer> allergies;

    public static ChildDetailResponse convertEntityToDto(ChildDetailDto dto) {
        List<Integer> allergies = dto.getAllergies().stream()
                .map(allergy -> allergy.getAllergy().getId())
                .toList();

        return ChildDetailResponse.builder()
                .childId(dto.getChild().getId())
                .imageUrl(dto.getChild().getImageUrl())
                .name(dto.getChild().getName())
                .birthDate(dto.getChild().getBirthDate())
                .gender(dto.getChild().getGender())
                .height(dto.getChild().getHeight())
                .weight(dto.getChild().getWeight())
                .allergies(allergies)
                .build();
    }
}
