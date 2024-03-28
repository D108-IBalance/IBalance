package com.ssafy.ibalance.diet.repository.dietmenu;

import com.ssafy.ibalance.diet.dto.DietDetailDto;
import com.ssafy.ibalance.member.entity.Member;

public interface DietMenuCustomRepository {
    DietDetailDto getDietAndMenu(Member member, Long dietId);
}
