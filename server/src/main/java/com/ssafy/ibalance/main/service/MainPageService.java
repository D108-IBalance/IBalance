package com.ssafy.ibalance.main.service;

import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;
import com.ssafy.ibalance.child.dto.response.GrowthResponse;
import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.main.dto.response.MainPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainPageService {

    private final DietRepository dietRepository;
    private final GrowthRepository growthRepository;

    public MainPageResponse getMain(Integer childId, LocalDate date) {
        return MainPageResponse.builder()
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

    public List<GrowthResponse> getGrowth(Integer childId) {
        return growthRepository.findTop3ByChildIdOrderByIdDesc(childId)
                .stream()
                .map(GrowthResponse::ConvertEntityToDto)
                .collect(Collectors.toList());
    }

}