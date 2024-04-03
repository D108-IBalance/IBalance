package com.ssafy.ibalance.child.repository;

import com.ssafy.ibalance.child.dto.ChildDetailDto;
import com.ssafy.ibalance.member.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface ChildCustomRepository {

    List<String> getMenuIdByChildIdAndDate(Integer childId, LocalDate today);
    ChildDetailDto getChildDetail(Integer childId, Member member, List<Long> childAllergyLsit);
}
