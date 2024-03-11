package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.child.entity.Child;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DeleteChildResponse {

    private final Integer id;
    private final String name;
    private final Integer memberId;

    public static DeleteChildResponse ConvertEntityToDto(Child child) {
        return builder()
                .id(child.getId())
                .name(child.getName())
                .memberId(child.getMember().getId())
                .build();
    }
}
