package com.konai.kurong.faketee.schedule.repository.template;

import com.konai.kurong.faketee.schedule.dto.QTemplatePositionResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplatePositionResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.konai.kurong.faketee.schedule.entity.QTemplatePosition.templatePosition;

@RequiredArgsConstructor
@Repository
public class TemplatePositionCustomRepositoryImpl implements TemplatePositionCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 template 에 속해 있는 template position 객체(들)을 find 한다.
     *
     * @param tempId : template ID
     * @return template position response dto list
     */
    @Override
    public List<TemplatePositionResponseDto> findAllByTmpId(Long tempId) {

        return jpaQueryFactory
                .select(new QTemplatePositionResponseDto(templatePosition))
                .from(templatePosition)
                .where(templatePosition.template.id.eq(tempId))
                .orderBy(templatePosition.CRE_DTTM.desc())
                .fetch();
    }

    /**
     * 특정 template 과 연관관계에 있는 template position 객체(들)을 delete 한다.
     *
     * @param tempId : template ID
     */
    @Override
    public void deleteByTmpId(Long tempId) {

        jpaQueryFactory
                .delete(templatePosition)
                .where(templatePosition.template.id.eq(tempId))
                .execute();
    }
}
