package com.konai.kurong.faketee.schedule.repository.template;


import com.konai.kurong.faketee.schedule.dto.QTemplateDepartmentResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateDepartmentResponseDto;
import com.konai.kurong.faketee.schedule.entity.QTemplate;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.konai.kurong.faketee.schedule.entity.QTemplateDepartment.templateDepartment;

@RequiredArgsConstructor
@Repository
public class TemplateDepartmentCustomRepositoryImpl implements TemplateDepartmentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 특정 template 에 속해 있는 template department 객체(들)을 find 한다.
     *
     * @param tempId : template ID
     * @return template department response dto list
     */
    @Override
    public List<TemplateDepartmentResponseDto> findAllByTmpId(Long tempId) {

        return jpaQueryFactory
                .select(new QTemplateDepartmentResponseDto(templateDepartment))
                .from(templateDepartment)
                .where(templateDepartment.template.id.eq(tempId))
                .orderBy(templateDepartment.CRE_DTTM.desc())
                .fetch();
    }

    /**
     * 특정 template 과 연관관계에 있는 template department 객체(들)을 delete 한다.
     *
     * @param tempId : template ID
     */
    @Override
    public void deleteByTmpId(Long tempId) {

        jpaQueryFactory
                .delete(templateDepartment)
                .where(templateDepartment.template.id.eq(tempId))
                .execute();
    }
}
