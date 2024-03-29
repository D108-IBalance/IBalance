package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.Growth;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GrowthCustomRepository {

    Page<Growth> getGrowthList(Integer childId, Pageable pageable);
}
