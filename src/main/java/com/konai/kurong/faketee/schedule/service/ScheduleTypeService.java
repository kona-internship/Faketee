package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeResponseDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.repository.schedule.ScheduleTypeRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplateRepository;
import com.konai.kurong.faketee.utils.exception.custom.schedule.ConnectedTmpExistException;
import com.konai.kurong.faketee.utils.exception.custom.schedule.ScheduleTypeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleTypeService {
    private final ScheduleTypeRepository scheduleTypeRepository;
    private final CorporationRepository corporationRepository;
    private final TemplateRepository templateRepository;

    /**
     * 근무일정 유형 저장
     *
     * @param corId 회사
     * @param requestDto 근무일정 이름
     */
    @Transactional
    public void registerSchType(Long corId, ScheduleTypeSaveRequestDto requestDto) {
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        ScheduleType scheduleType = ScheduleType.builder()
                .name(requestDto.getName())
                .corporation(corporation)
                .build();

        scheduleTypeRepository.save(scheduleType);
    }

    /**
     * 근무일정 유형 삭제
     *
     * @param typeId 삭제할 근무일정 유형의 id
     */
    @Transactional
    public void removeSchType(Long typeId) {
        Long countTemp = templateRepository.countTemplateByScheduleTypeId(typeId);

        if(countTemp != 0){
            throw new ConnectedTmpExistException();
        }
        //아무도 참조하지 않으므로 삭제 가능
        scheduleTypeRepository.deleteById(typeId);
    }

    /**
     * 근무일정 유형 리스트
     *
     * @param corId
     * @return
     */
    public List<ScheduleTypeResponseDto> getSchTypeList(Long corId) {
        return ScheduleTypeResponseDto.convertToDtoList(scheduleTypeRepository.findAllByCorporationId(corId));

    }

    /**
     * id로 찾는 근무일정 유형
     *
     * @param id
     * @return
     */
    public ScheduleType findById(Long id){

        return scheduleTypeRepository.findById(id).orElseThrow(() -> new ScheduleTypeNotFoundException());
    }

}
