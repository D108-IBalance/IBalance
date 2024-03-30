package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.dto.HeightGrowthDto;
import com.ssafy.ibalance.child.dto.WeightGrowthDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrowthCustomRepository {

    Page<HeightGrowthDto> getHeightList(Integer childId, Pageable pageable);
    Page<WeightGrowthDto> getWeightList(Integer childId, Pageable pageable);
}
