package com.ssafy.ibalance.diet.dto.request;

import com.ssafy.ibalance.diet.dto.RecommendNeedDto;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class RecommendRequest {

    private Integer childId;
    private List<String> allergyList;
    private List<String> cacheList;
    private RecommendNeedDto need;
    private MenuType needType;
    private List<String> currentMenuIdOfDiet;
}
