package com.ssafy.ibalance.diet.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.diet.dto.DietByDateDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
}
