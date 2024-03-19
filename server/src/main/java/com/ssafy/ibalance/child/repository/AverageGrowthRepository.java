package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.AverageGrowth;
import com.ssafy.ibalance.child.type.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AverageGrowthRepository extends JpaRepository<AverageGrowth, Integer> {
    List<AverageGrowth> findByGenderAndGrowMonthIn(Gender gender, List<Long> month);
}
