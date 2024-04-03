package com.ssafy.ibalance.diet.dto.request;

import lombok.Builder;

import java.util.List;

@Builder
public record PickyMainFastApiRequest(
        List<String> allergyNameList,
        List<String> pickyMatrlList
) {
}
