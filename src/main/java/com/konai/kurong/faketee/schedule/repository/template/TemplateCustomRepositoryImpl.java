package com.konai.kurong.faketee.schedule.repository.template;

import com.konai.kurong.faketee.schedule.dto.QTemplateResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.konai.kurong.faketee.schedule.entity.QTemplate.template;

@RequiredArgsConstructor
@Repository
public class TemplateCustomRepositoryImpl implements TemplateCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 corporation 에 속해 있는 근무일정 템플릿(들)을 find 한다.
     *
     * @param corId : corporation ID
     * @return template response dto list
     */
    @Override
    public List<TemplateResponseDto> findAllByCorId(Long corId) {

        return jpaQueryFactory
                .select(new QTemplateResponseDto(template))
                .from(template)
                .where(template.scheduleType.corporation.id.eq(corId))
                .orderBy(template.CRE_DTTM.asc())
                .fetch();
    }
}
