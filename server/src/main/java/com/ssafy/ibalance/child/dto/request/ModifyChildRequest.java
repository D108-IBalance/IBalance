package com.ssafy.ibalance.child.dto.request;

import com.ssafy.ibalance.child.dto.annotation.CheckAllergies;
import com.ssafy.ibalance.child.dto.annotation.CheckDouble;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ModifyChildRequest {

    @NotNull(message = "키를 입력해주세요")
    @CheckDouble
    private Double height;

    @NotNull(message = "몸무게를 입력해주세요")
    @CheckDouble
    private Double weight;

    @NotNull(message = "알러지 정보를 입력해주세요")
    @CheckAllergies
    private List<Integer> haveAllergies;
}
