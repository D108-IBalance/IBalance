package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.dto.HeightGrowthDto;
import com.ssafy.ibalance.child.dto.WeightGrowthDto;
import com.ssafy.ibalance.notification.dto.NotifyTargetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GrowthCustomRepository {

    Page<HeightGrowthDto> getHeightList(Integer childId, Pageable pageable);
    Page<WeightGrowthDto> getWeightList(Integer childId, Pageable pageable);
    List<NotifyTargetDto> getNotifyTargetList();
}
