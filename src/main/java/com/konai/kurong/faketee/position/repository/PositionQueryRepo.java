package com.konai.kurong.faketee.position.repository;

import com.konai.kurong.faketee.position.entity.Position;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.konai.kurong.faketee.position.entity.QPosition.position;
@Repository
public class PositionQueryRepo extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     *
     * @param jpaQueryFactory
     */
    public PositionQueryRepo(JPAQueryFactory jpaQueryFactory) {
        super(Position.class);
       this.jpaQueryFactory = jpaQueryFactory;

    }
    public void updatePosName(Long id, String name){
        jpaQueryFactory
                .update(position)
                .set(position.name, name)
                .where(position.id.eq(id))
                .execute();
    }
}
