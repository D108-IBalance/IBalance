package com.ssafy.ibalance.child.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.growth.entity.Growth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ChildDetailDto {
    private Child child;
    private Growth growth;

    @QueryProjection
    public ChildDetailDto(Growth growth) {
        this.child = child;
        this.growth = growth;
    }
}
