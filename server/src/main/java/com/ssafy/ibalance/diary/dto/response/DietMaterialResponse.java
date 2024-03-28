package com.ssafy.ibalance.diary.dto.response;

import com.ssafy.ibalance.diet.entity.DietMaterial;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DietMaterialResponse {
    private Long id;
    private boolean picky;
    private String material;

    public static DietMaterialResponse convertToResponse(DietMaterial input) {
        return DietMaterialResponse.builder()
                .id(input.getId())
                .material(input.getMaterial())
                .picky(input.isPicky())
                .build();
    }
}
