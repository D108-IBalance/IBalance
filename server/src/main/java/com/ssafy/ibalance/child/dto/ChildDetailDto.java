package com.ssafy.ibalance.child.dto;

import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChildDetailDto {
    private Child child;
    private List<ChildAllergy> allergies;
}
