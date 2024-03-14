package com.ssafy.ibalance.growth.service;

import com.ssafy.ibalance.growth.dto.response.GrowthResponse;
import com.ssafy.ibalance.growth.repository.GrowthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrowthService {

    private final GrowthRepository growthRepository;

    public List<GrowthResponse> getGrowthList(Integer childId, Pageable pageable) {
        return growthRepository.findByChildIdOrderByIdDesc(childId, pageable)
                .stream()
                .map(GrowthResponse::ConvertEntityToDto)
                .collect(Collectors.toList());
    }
}
