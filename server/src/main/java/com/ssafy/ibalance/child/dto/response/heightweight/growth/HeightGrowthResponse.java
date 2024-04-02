package com.ssafy.ibalance.child.dto.response.heightweight.growth;

import com.ssafy.ibalance.child.dto.HeightGrowthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeightGrowthResponse extends GrowthResponse {

    private double height;

    public static HeightGrowthResponse convertEntityToDto(HeightGrowthDto heightGrowth) {
        HeightGrowthResponse response =
                GrowthResponse.convertEntityToDto(heightGrowth, new HeightGrowthResponse());

        response.setHeight(heightGrowth.getHeight());
        return response;
    }
}
