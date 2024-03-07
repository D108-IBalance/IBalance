package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class ChildListResponseDto {

    private final Integer childId;
    private final String name;
    private final String imageUrl;
    private Gender test;


    public static ChildListResponseDto of(Child child) {
        return builder()
                .childId(child.getId())
                .name(child.getName())
                .imageUrl(child.getImageUrl())
                .test(child.getGender())
                .build();
    }
}
