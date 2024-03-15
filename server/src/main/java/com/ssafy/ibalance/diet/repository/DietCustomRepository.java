package com.ssafy.ibalance.diet.repository;

import com.ssafy.ibalance.diet.dto.DietByDateDto;
import com.ssafy.ibalance.diet.dto.response.ChildDietResponse;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;

import java.time.LocalDate;
import java.util.List;

public interface DietCustomRepository {
    List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date);

    List<ChildDietResponse> getDietMenuByDate(Integer childId, LocalDate startDate, LocalDate endDate);
}
