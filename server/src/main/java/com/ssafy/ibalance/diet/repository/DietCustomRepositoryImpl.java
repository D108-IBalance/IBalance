package com.ssafy.ibalance.diet.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.diet.dto.DietByDateDto;
import com.ssafy.ibalance.diet.dto.MenuDto;
import com.ssafy.ibalance.diet.dto.response.ChildDietResponse;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.type.MenuType;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.*;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;

@RequiredArgsConstructor
public class DietCustomRepositoryImpl implements DietCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date) {
        List<DietByDateDto> dietByDateDtoList = jpaQueryFactory.select(
                Projections.bean(
                        DietByDateDto.class,
                        diet.as("diet"),
                        dietMenu.as("dietMenu")
                        )
                )
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.child.id.eq(childId).and(diet.dietDate.eq(date)))
                .fetch();

        System.out.println(dietByDateDtoList);

        return null;
    }

    @Override
    public List<ChildDietResponse> getDietMenuByDate(Integer childId, LocalDate startDate, LocalDate endDate) {
        Map<Diet, List<DietMenu>> transform = jpaQueryFactory.select(diet, dietMenu)
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.child.id.eq(childId).and(diet.dietDate.between(startDate, endDate)))
                .transform(
                        groupBy(diet).as(
                                list(dietMenu)
                        )
                );

        List<DietByDateDto> dietByDateDtoList = transform.entrySet().stream()
                .map(entry -> DietByDateDto.builder()
                        .dietId(entry.getKey().getId())
                        .dietDate(entry.getKey().getDietDate())
                        .sequence(entry.getKey().getSequence())
                        .dietMenuList(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        List<ChildDietResponse> childDietResponseList = new ArrayList<>();

        // TODO : MongoDB에서 메뉴 데이터 가져오기
        for(DietByDateDto dto : dietByDateDtoList) {
            List<MenuDto> menuDtoList = new ArrayList<>();

            for(DietMenu dietMenu : dto.getDietMenuList()) {
                menuDtoList.add(MenuDto.builder()
                        .menuId(dietMenu.getMenuId())
                        .menuName("메뉴 이름")
                        .menuType(MenuType.RICE)
                        .build());
            }

            childDietResponseList.add(ChildDietResponse.builder()
                    .dietId(dto.getDietId())
                    .dietDate(dto.getDietDate())
                    .sequence(dto.getSequence())
                    .menuList(menuDtoList)
                    .build());
        }

        return childDietResponseList;
    }

    @Override
    public List<Integer> getMenuIdByDietId(Long dietId) {
        return jpaQueryFactory.select(dietMenu.menuId)
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.id.eq(dietId))
                .fetch();
    }
}
