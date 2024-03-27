package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.dto.response.ChildDetailResponse;
import com.ssafy.ibalance.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface ChildCustomRepository {

    List<String> getMenuIdByChildIdAndDate(Integer childId, LocalDate today);
    ChildDetailResponse getChildDetail(Integer childId, Member member);
}
