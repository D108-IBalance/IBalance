package com.ssafy.ibalance.child.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.entity.Growth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ssafy.ibalance.child.entity.QGrowth.growth;

@RequiredArgsConstructor
public class GrowthCustomRepositoryImpl implements GrowthCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Growth> getGrowthList(Integer childId, Pageable pageable) {
        List<Growth> content = jpaQueryFactory.select(growth)
                .from(growth)
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(growth.id.desc())
                .fetch();

        JPAQuery<Growth> count = jpaQueryFactory.select(growth)
                .from(growth)
                .where(growth.child.id.eq(childId))
                .groupBy(growth.createdTime.week(), growth)
                .orderBy(growth.id.desc());

        return PageableExecutionUtils.getPage(content, pageable, count::fetchCount);
    }
}
