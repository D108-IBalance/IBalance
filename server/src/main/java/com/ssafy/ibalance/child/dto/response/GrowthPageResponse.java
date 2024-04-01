package com.ssafy.ibalance.child.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class GrowthPageResponse {

    private boolean last;
    private List<GrowthResponse> growthList;
    private List<AverageGrowthResponse> averageList;
}
