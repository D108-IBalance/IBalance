package com.ssafy.ibalance.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTesterEntity is a Querydsl query type for TesterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTesterEntity extends EntityPathBase<TesterEntity> {

    private static final long serialVersionUID = -247686811L;

    public static final QTesterEntity testerEntity = new QTesterEntity("testerEntity");

    public final StringPath address = createString("address");

    public final NumberPath<Long> entityId = createNumber("entityId", Long.class);

    public final StringPath name = createString("name");

    public QTesterEntity(String variable) {
        super(TesterEntity.class, forVariable(variable));
    }

    public QTesterEntity(Path<? extends TesterEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTesterEntity(PathMetadata metadata) {
        super(TesterEntity.class, metadata);
    }

}

