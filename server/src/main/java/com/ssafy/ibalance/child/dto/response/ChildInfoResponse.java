package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.type.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ChildInfoResponse {

    private final Integer childId;
    private final String name;
    private final String imageUrl;
    private Gender gender;

    public static ChildInfoResponse ConvertEntityToDto(Child child) {
        return builder()
                .childId(child.getId())
                .name(child.getName())
                .imageUrl(child.getImageUrl())
                .gender(child.getGender())
                .build();
    }
}
