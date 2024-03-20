package com.ssafy.ibalance.diet.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.exception.ChildAccessDeniedException;
import com.ssafy.ibalance.diary.dto.CalendarDto;
import com.ssafy.ibalance.diary.dto.response.CalendarResponse;
import com.ssafy.ibalance.diet.dto.DietByDateDto;
import com.ssafy.ibalance.diet.dto.response.DietByDateResponse;
import com.ssafy.ibalance.diet.dto.MenuDto;
import com.ssafy.ibalance.diet.dto.response.RecommendedDietResponse;
import com.ssafy.ibalance.diet.entity.Diet;
import com.ssafy.ibalance.diet.entity.DietMenu;
import com.ssafy.ibalance.diet.dto.response.DietMenuResponse;
import com.ssafy.ibalance.diet.type.MenuType;
import com.ssafy.ibalance.member.entity.Member;
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
        // DB에서 데이터 조회
        Map<Diet, List<DietMenu>> transform = jpaQueryFactory.select(diet, dietMenu)
                .from(diet)
                .join(dietMenu)
                .on(diet.id.eq(dietMenu.diet.id))
                .where(diet.child.id.eq(childId).and(diet.dietDate.eq(date)))
                .transform(
                        groupBy(diet).as(
                                list(dietMenu)
                        )
                );

        // Dto로 변환
        List<DietByDateDto> dietByDateDtoList = transform.entrySet().stream()
                .map(entry -> DietByDateDto.builder()
                        .dietId(entry.getKey().getId())
                        .dietDate(entry.getKey().getDietDate())
                        .sequence(entry.getKey().getSequence())
                        .dietMenuList(entry.getValue())
                        .build())
                .collect(Collectors.toList());

        // return하기 위한 response 객체 생성
        // TODO : NoSQL에서 메뉴 아이디에 해당하는 메뉴 정보 조회 구현 필요
        List<DietByDateResponse> dietByDateResponseList = new ArrayList<>();

        for(DietByDateDto dto : dietByDateDtoList) {
            List<DietMenuResponse> dietMenuResponseList = new ArrayList<>();

            for(DietMenu dietMenu : dto.getDietMenuList()) {
                dietMenuResponseList.add(DietMenuResponse.builder()
                        .menuId(dietMenu.getMenuId())
                        .menuName("NoSQL에서 가져온 메뉴 이름")
                        .score(dietMenu.getScore())
                        .build());
            }

            dietByDateResponseList.add(DietByDateResponse.builder()
                    .dietId(dto.getDietId())
                    .dietDate(dto.getDietDate())
                    .sequence(dto.getSequence())
                    .dietMenuList(dietMenuResponseList)
                    .build());
        }

        return dietByDateResponseList;
    }

    @Override
    public List<RecommendedDietResponse> getDietMenuByDate(Integer childId, LocalDate startDate, LocalDate endDate) {
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
                .toList();

        List<RecommendedDietResponse> childDietResponseList = new ArrayList<>();

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

            childDietResponseList.add(RecommendedDietResponse.builder()
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

    @Override
    public List<CalendarResponse> getCalendarList(Integer childId, int year, int month, Member member) {
        JPQLQuery<LocalDate> allReviewed = JPAExpressions.select(diet.dietDate)
                .from(diet)
                .where(diet.child.id.eq(childId)
                        .and(diet.dietDate.year().eq(year))
                        .and(diet.dietDate.month().eq(month))
                        .and(diet.isReviewed.eq(false)))
                .groupBy(diet.dietDate);

        List<CalendarDto> calendarDtoList = jpaQueryFactory.select(
                        Projections.fields(CalendarDto.class,
                                diet.child.member,
                                diet.dietDate,
                                diet.dietDate.in(allReviewed).not().as("allReviewed"))
                )
                .from(diet)
                .where(diet.child.id.eq(childId)
                        .and(diet.dietDate.year().eq(year))
                        .and(diet.dietDate.month().eq(month)))
                .groupBy(diet.child.member, diet.dietDate)
                .fetch();

        if(!member.equals(calendarDtoList.getFirst().getMember())) {
            throw new ChildAccessDeniedException("조회 권한이 없습니다.");
        }

        return calendarDtoList.stream().map(CalendarResponse::convertEntityToDto).toList();
    }
}
