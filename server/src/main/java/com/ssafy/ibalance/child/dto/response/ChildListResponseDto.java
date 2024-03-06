package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Child;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChildListResponseDto {

    private final Integer childId;
    private final String name;
    private final String imageUrl;

    @Builder
    private ChildListResponseDto(Child child) {
        this.childId = child.getId();
        this.name = child.getName();
        this.imageUrl = child.getImageUrl();
    }

    public static ChildListResponseDto of(Child child) {
        return builder().child(child).build();
    }
}
