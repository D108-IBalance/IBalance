package com.ssafy.ibalance.diet.dto.response;

import lombok.Builder;

@Builder
public record PickyMaterialResponse(
        String material,
        Long count
) {
}
