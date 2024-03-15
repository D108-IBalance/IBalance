package com.ssafy.ibalance.diet.service;

import com.ssafy.ibalance.diet.dto.response.ChildDietResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DietService {

    private final DietRepository dietRepository;

    public List<ChildDietResponse> getChildDiet(Integer childId, LocalDate today) {

        LocalDate endday = today.plusDays(6);
        return dietRepository.getDietMenuByDate(childId, today, endday);
    }
}
