package com.ssafy.ibalance.child.dto.response;

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
