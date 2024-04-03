package com.ssafy.ibalance.diet.repository.dietmaterial;

import com.ssafy.ibalance.diet.dto.response.PickyResultResponse;

import java.time.LocalDate;

public interface DietMaterialCustomRepository {

    PickyResultResponse getPickyResult(Integer childId, LocalDate startDate);
}
