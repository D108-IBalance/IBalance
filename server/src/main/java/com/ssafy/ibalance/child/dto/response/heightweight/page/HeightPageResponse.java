package com.ssafy.ibalance.child.dto.response.heightweight.page;

import com.ssafy.ibalance.child.dto.response.AverageGrowthResponse;
import com.ssafy.ibalance.child.dto.response.heightweight.growth.HeightGrowthResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class HeightPageResponse {

    private boolean last;
    private List<HeightGrowthResponse> growthList;
    private List<AverageGrowthResponse> averageList;
}
