package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;
import com.ssafy.ibalance.diet.repository.dietmaterial.DietMaterialRepository;
import com.ssafy.ibalance.diet.type.PeriodUnit;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickyService {

    private final DietMaterialRepository dietMaterialRepository;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;


    public PickyResultResponse getPickyResult(Member member, Integer childId, PeriodUnit periodUnit) {
        return dietMaterialRepository.getPickyResult(member, childId, PeriodUnit.getStartDate(periodUnit));
    }
}
