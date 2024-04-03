package com.ssafy.ibalance.child.dto.response;

import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ChildDietResponse {

    private ChildMainResponse childMainResponse;
    private List<DietByDateResponse> dietList;
}
