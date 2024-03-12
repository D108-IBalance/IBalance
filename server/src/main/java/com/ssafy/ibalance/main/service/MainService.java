package com.ssafy.ibalance.main.service;

import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;
import com.ssafy.ibalance.child.entity.Growth;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.child.repository.ChildRepository;
import com.ssafy.ibalance.child.repository.GrowthRepository;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.repository.DietRepository;
import com.ssafy.ibalance.main.dto.response.MainResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final ChildRepository childRepository;
    private final DietRepository dietRepository;

    public MainResponse getMain(Integer childId, LocalDate date) {
        ChildDetailResponse childDetailResponse = childRepository.getChildDetail(childId).orElseThrow(
                () -> new ChildNotFoundException("해당하는 자녀가 없습니다.")
        );

        List<DietByDateResponse> dietByDateList = dietRepository.getDietByDate(childId, date);

        return MainResponse.builder()
                .childDetailResponse(childDetailResponse).build();

    }

}
