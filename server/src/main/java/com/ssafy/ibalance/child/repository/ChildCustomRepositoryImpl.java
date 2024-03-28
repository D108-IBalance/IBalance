package com.ssafy.ibalance.child.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.dto.ChildDetailDto;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.entity.ChildAllergy;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.child.entity.QChildAllergy.childAllergy;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;

@RequiredArgsConstructor
public class ChildCustomRepositoryImpl implements ChildCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> getMenuIdByChildIdAndDate(Integer childId, LocalDate today) {
        return jpaQueryFactory.select(dietMenu.menuId)
                .from(child)
                .join(diet)
                .on(child.id.eq(diet.child.id))
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(child.id.eq(childId))
                .where(diet.dietDate.between(today.minusWeeks(2), today))
                .fetch();
    }

    @Override
    public ChildDetailDto getChildDetail(Integer childId, Member member, List<Long> childAllergyList) {
        Map<Child, List<ChildAllergy>> transform = jpaQueryFactory.select(child, childAllergy)
                .from(child)
                .leftJoin(childAllergy).on(child.id.eq(childAllergy.child.id))
                .where(child.id.eq(childId).and(childAllergy.id.in(childAllergyList)))
                .transform(
                        groupBy(child).as(
                                list(childAllergy)
                        )
                );

        ChildDetailDto childDetailDto = transform.entrySet().stream()
                .map(entry -> ChildDetailDto.builder()
                        .child(entry.getKey())
                        .allergies(entry.getValue())
                        .build())
                .findFirst()
                .orElseThrow(() -> new ChildNotFoundException("해당하는 자녀가 없습니다."));

        if(!member.equals(childDetailDto.getChild().getMember())) {
            throw new ChildAccessDeniedException("조회 권한이 없습니다.");
        }

        return childDetailDto;
    }
}
