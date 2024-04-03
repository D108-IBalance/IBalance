package com.ssafy.ibalance.diet.repository.diet;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.Group;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.child.exception.ChildNotFoundException;
import com.ssafy.ibalance.common.util.FastAPIConnectionUtil;
import com.ssafy.ibalance.diary.dto.CalendarDto;
import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.dto.DietByDateDto;
import com.ssafy.ibalance.diet.dto.DietTotalInfoDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMaterial;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.exception.DietNotFoundException;
import com.ssafy.ibalance.member.entity.Member;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.ssafy.ibalance.child.entity.QChild.child;
import static com.ssafy.ibalance.diet.entity.QDiet.diet;
import static com.ssafy.ibalance.diet.entity.QDietMaterial.dietMaterial;
import static com.ssafy.ibalance.diet.entity.QDietMenu.dietMenu;
import static com.ssafy.ibalance.member.entity.QMember.member;

@RequiredArgsConstructor
public class DietCustomRepositoryImpl implements DietCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final FastAPIConnectionUtil fastAPIConnectionUtil;

    @Override
    public List<DietByDateResponse> getDietByDate(Integer childId, LocalDate date, Member member) {
        JPAQuery<Tuple> tuple = jpaQueryFactory.select(child, diet, dietMenu)
                .from(child)
                .leftJoin(diet).on(child.id.eq(diet.child.id).and(diet.dietDate.eq(date)))
                .leftJoin(dietMenu).on(diet.id.eq(dietMenu.diet.id))
                .where(child.id.eq(childId));

        if(tuple.fetch().isEmpty()) {
            throw new ChildNotFoundException("해당하는 자녀가 없습니다.");
        }

        Map<Diet, List<DietMenu>> transform = tuple.transform(
                groupBy(diet).as(
                        list(dietMenu)
                )
        );

        List<DietByDateDto> dietByDateDtoList = transform.entrySet().stream()
                .map(entry -> DietByDateDto.builder()
                        .diet(entry.getKey())
                        .dietMenuList(entry.getValue())
                        .build())
                .toList();

        if(dietByDateDtoList.getFirst().getDiet() != null && !member.equals(dietByDateDtoList.getFirst().getDiet().getChild().getMember())) {
            throw new ChildAccessDeniedException("조회 권한이 없습니다.");
        }

        if(dietByDateDtoList.getFirst().getDiet() == null) {
            return new ArrayList<>();
        }

        return dietByDateDtoList.stream()
                .map(dto -> DietByDateResponse.builder()
                        .dietId(dto.getDiet().getId())
                        .dietDate(dto.getDiet().getDietDate())
                        .mealTime(dto.getDiet().getMealTime().toString())
                        .menuList(getDietMenuFromMongo(dto))
                        .build())
                .toList();
    }

    public List<DietMenuResponse> getDietMenuFromMongo(DietByDateDto dietByDateDto) {
        return dietByDateDto.getDietMenuList().stream()
                .map(menu -> fastAPIConnectionUtil.getApiConnectionResult("/info/" + menu.getMenuId(), DietMenuResponse.builder().build()))
                .toList();
    }

    @Override
    public List<DietByDateResponse> getDietByDateBetween(Integer childId, LocalDate startDate, LocalDate endDate) {
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
                        .diet(entry.getKey())
                        .dietMenuList(entry.getValue())
                        .build())
                .toList();

        List<DietByDateResponse> childDietResponseList = new ArrayList<>();

        dietByDateDtoList.forEach(dto -> {
            List<DietMenuResponse> menuDtoList = getDietMenuFromMongo(dto);

            childDietResponseList.add(DietByDateResponse.builder()
                    .dietId(dto.getDiet().getId())
                    .dietDate(dto.getDiet().getDietDate())
                    .mealTime(dto.getDiet().getMealTime().toString())
                    .menuList(menuDtoList)
                    .build());
        });

        return childDietResponseList;
    }

    @Override
    public List<String> getMenuIdByDietId(Long dietId) {
        return jpaQueryFactory.select(dietMenu.menuId)
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.id.eq(dietId))
                .fetch();
    }

    @Override
    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        JPQLQuery<LocalDate> allReviewed = JPAExpressions.select(diet.dietDate)
                .from(diet)
                .where(diet.child.id.eq(childId)
                        .and(diet.dietDate.year().eq(year))
                        .and(diet.dietDate.month().eq(month))
                        .and(diet.isReviewed.eq(false)))
                .groupBy(diet.dietDate);

        List<CalendarDto> calendarList = jpaQueryFactory.select(
                        Projections.fields(CalendarDto.class,
                                child,
                                diet.dietDate,
                                diet.dietDate.in(allReviewed).not().as("allReviewed"))
                )
                .from(child).leftJoin(diet)
                .on(diet.child.id.eq(child.id).and(diet.dietDate.year().eq(year)).and(diet.dietDate.month().eq(month)))
                .where(child.id.eq(childId))
                .groupBy(child, diet.dietDate)
                .fetch();

        if(calendarList.isEmpty()) {
            throw new ChildNotFoundException("해당하는 아이를 찾을 수 없습니다.");
        }

        if(calendarList.getFirst().getChild() != null && !member.equals(calendarList.getFirst().getChild().getMember())) {
            throw new ChildAccessDeniedException("조회 권한이 없습니다.");
        }

        if(calendarList.getFirst().getDietDate() == null) {
            calendarList.clear();
        }

        return calendarList.stream().map(CalendarResponse::convertEntityToDto).toList();
    }

    @Override
    public DietTotalInfoDto getDietTotalInfo(Long dietId) {
        Map<Diet, Group> transform = jpaQueryFactory.select(Projections.bean(
                        DietTotalInfoDto.class,
                        diet,
                        list(dietMenu).as("dietMenuList"),
                        list(dietMaterial).as("dietMaterialList")
                )).from(diet)
                .leftJoin(dietMenu).on(dietMenu.diet.eq(diet))
                .leftJoin(dietMaterial).on(dietMaterial.diet.eq(diet))
                .where(diet.id.eq(dietId)
                        .and(dietMenu.diet.eq(dietMaterial.diet)))
                .transform(
                        groupBy(diet).as(list(dietMenu), list(dietMaterial))
                );

        Diet diet = transform.keySet().stream().findFirst().orElseThrow(
                () -> new DietNotFoundException("입력한 아이디로 된 식단이 존재하지 않습니다.")
        );


        List<DietMaterial> dietMaterialList = getDistinctResult(transform, diet, dietMaterial);
        List<DietMenu> dietMenuList = getDistinctResult(transform, diet, dietMenu);

        return new DietTotalInfoDto(diet, dietMaterialList, dietMenuList);
    }

    @Override
    public List<Integer> getNotifyTargetList() {
        return jpaQueryFactory.select(member.id)
                .from(member)
                .leftJoin(child).on(member.id.eq(child.member.id))
                .leftJoin(diet).on(child.id.eq(diet.child.id))
                .where(diet.dietDate.eq(LocalDate.now()).and(diet.isReviewed.eq(false)))
                .groupBy(member.id)
                .fetch();
    }

    private <T> List<T> getDistinctResult(Map<Diet, Group> transform, Diet diet, EntityPathBase<T> pathBase) {
        return transform.get(diet).getList(pathBase).stream().distinct().toList();
    }
}
