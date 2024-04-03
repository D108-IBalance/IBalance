package com.ssafy.ibalance.child.dto.response.heightweight.page;

import com.ssafy.ibalance.child.dto.response.AverageGrowthResponse;
import com.ssafy.ibalance.child.dto.response.heightweight.growth.WeightGrowthResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WeightPageResponse {

    private boolean last;
    private List<WeightGrowthResponse> growthList;
    private List<AverageGrowthResponse> averageList;
}
