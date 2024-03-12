package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
public class RegistChildResponse {

    private final Integer id;
    private final String name;
    private final LocalDate birthDate;
    private final Gender gender;
    private final double height;
    private final double weight;
    private final String imageUrl;
    private final Integer memberId;

    public static RegistChildResponse convertEntityToDto(Child child) {
        return builder()
                .id(child.getId())
                .name(child.getName())
                .birthDate(child.getBirthDate())
                .gender(child.getGender())
                .height(child.getHeight())
                .weight(child.getWeight())
                .imageUrl(child.getImageUrl())
                .memberId(child.getMember().getId())
                .build();
    }
}
