package com.ssafy.ibalance.main.service;

import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;
import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.main.dto.response.MainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MainService {

    private final DietRepository dietRepository;
    private final GrowthRepository growthRepository;

    public MainResponse getMain(Integer childId, LocalDate date) {
        return MainResponse.builder()
                .childDetailResponse(getChildDetail(childId))
                .dietList(dietRepository.getDietByDate(childId, date)).build();
    }

    public ChildDetailResponse getChildDetail(Integer childId) {
        Growth growth = growthRepository
                .findTopByChildIdOrderByCreatedTimeDesc(childId)
                .orElseThrow(() -> new ChildNotFoundException("해당하는 자녀가 없습니다."));

        return ChildDetailResponse.builder()
                .childId(growth.getChild().getId())
                .imageUrl(growth.getChild().getImageUrl())
                .name(growth.getChild().getName())
                .birthDate(growth.getChild().getBirthDate())
                .gender(growth.getChild().getGender())
                .height(growth.getHeight())
                .weight(growth.getWeight())
                .lastUpdateDate(growth.getCreatedTime().toLocalDate())
                .build();
    }

}