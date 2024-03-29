package com.ssafy.ibalance.diet.repository.dietmaterial;

import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;
import com.ssafy.ibalance.member.entity.Member;

import java.time.LocalDate;

public interface DietMaterialCustomRepository {

    PickyResultResponse getPickyResult(Member member, Integer childId, LocalDate startDate);
}
