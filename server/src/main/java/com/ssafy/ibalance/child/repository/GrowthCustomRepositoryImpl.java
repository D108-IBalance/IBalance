package com.ssafy.ibalance.child.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ibalance.child.entity.Growth;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
                .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }
}
