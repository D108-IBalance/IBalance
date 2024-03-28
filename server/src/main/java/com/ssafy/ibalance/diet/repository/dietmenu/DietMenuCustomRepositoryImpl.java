package com.ssafy.ibalance.diet.repository.dietmenu;

import com.querydsl.core.group.Group;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.entity.Child;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.diet.dto.DietDetailDto;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.exception.DietNotFoundException;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMaterial.dietMaterial;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;

@RequiredArgsConstructor
public class DietMenuCustomRepositoryImpl implements DietMenuCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public DietDetailDto getDietAndMenu(Member member, Long dietId) {
        Map<Child, Group> transform = jpaQueryFactory.select(
                        Projections.bean(
                                DietDetailDto.class,
                                child,
                                diet,
                                list(dietMenu.menuId).as("menuIdList"),
                                list(dietMaterial).as("dietMaterialList")
                        )
                )
                .from(dietMenu)
                .join(diet).on(dietMenu.diet.eq(diet))
                .join(child).on(diet.child.eq(child))
                .where(diet.id.eq(dietId))
                .transform(
                        groupBy(child).as(
                                diet,
                                list(dietMenu.menuId),
                                list(dietMaterial)
                        )
                );

        Child child = transform.keySet().stream().findFirst().orElseThrow(
                () -> new DietNotFoundException("해당 식단을 찾을 수 없습니다.")
        );

        if(member.equals(child.getMember())) {
            throw new ChildAccessDeniedException("해당 식단 정보를 열람할 수 있는 권한이 없습니다.");
        }

        Diet targetDiet = transform.get(child).getOne(diet);
        List<String> targetMenuIdList = transform.get(child).getList(dietMenu.menuId);
        List<DietMaterial> dietMaterialList = transform.get(child).getList(dietMaterial);

        return new DietDetailDto(child, targetDiet, targetMenuIdList, dietMaterialList);
    }
}
