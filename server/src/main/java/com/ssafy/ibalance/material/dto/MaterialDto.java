package com.ssafy.ibalance.material.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MaterialDto {

    private Integer materialId;
    private String materialName;
}
