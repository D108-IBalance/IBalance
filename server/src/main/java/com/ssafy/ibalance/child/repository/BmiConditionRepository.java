package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.entity.BmiCondition;
import com.ssafy.ibalance.child.type.Gender;
import com.ssafy.ibalance.child.type.WeightCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BmiConditionRepository extends JpaRepository<BmiCondition, Integer> {

    BmiCondition findAllByGenderAndGrowMonthAndWeightCondition(Gender gender, int growMonth, WeightCondition weightCondition);
}
