package com.ssafy.ibalance.child.dto.response.heightweight.growth;

import com.ssafy.ibalance.child.dto.WeightGrowthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeightGrowthResponse extends GrowthResponse {

    private double weight;

    public static WeightGrowthResponse convertEntityToDto(WeightGrowthDto weightGrowth) {
        WeightGrowthResponse response = GrowthResponse.convertEntityToDto(weightGrowth, new WeightGrowthResponse());

        response.setWeight(weightGrowth.getWeight());
        return response;
    }
}
