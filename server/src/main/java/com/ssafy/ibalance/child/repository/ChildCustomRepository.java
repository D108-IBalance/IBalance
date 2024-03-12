package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;

import java.util.Optional;

public interface ChildCustomRepository {
    Optional<ChildDetailResponse> getChildDetail(Integer childId);
}
