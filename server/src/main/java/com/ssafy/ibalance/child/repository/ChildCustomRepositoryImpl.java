package com.ssafy.ibalance.child.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;

@RequiredArgsConstructor
public class ChildCustomRepositoryImpl implements ChildCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Integer> getMenuIdByCHildIdAndDate(Integer childId, LocalDate today) {
        return jpaQueryFactory.select(dietMenu.menuId)
                .from(child)
                .join(diet)
                .join(dietMenu)
                .on(child.id.eq(diet.child.id))
                .on(diet.id.eq(dietMenu.diet.id))
                .where(child.id.eq(childId))
                .where(diet.dietDate.between(today.minusWeeks(2), today))
                .fetch();
    }
}
