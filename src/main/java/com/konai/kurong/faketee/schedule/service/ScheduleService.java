package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeResponseDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.repository.ScheduleTypeRepository;
import com.konai.kurong.faketee.schedule.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleTypeRepository scheduleTypeRepository;
    private final CorporationRepository corporationRepository;
    private final TemplateRepository templateRepository;
    @Transactional
    public void registerSchType(Long corId, ScheduleTypeSaveRequestDto requestDto) {
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        ScheduleType scheduleType = ScheduleType.builder()
                .name(requestDto.getName())
                .corporation(corporation)
                .build();

        scheduleTypeRepository.save(scheduleType);
    }
    @Transactional
    public void removeSchType(Long corId, Long typeId) {
        Long countTemp = templateRepository.countTemplateByScheduleTypeId(typeId);
        if(countTemp != 0){
            //이 근무 일정 유형을 가지고 있는 템플릿이 존재함
            //추가 필요
            //커스텀 에러 사용
            return;
        }
        //아무도 참조하지 않으므로 삭제 가능
        scheduleTypeRepository.deleteById(typeId);
    }
    public List<ScheduleTypeResponseDto> getSchTypeList(Long corId) {
        return ScheduleTypeResponseDto.convertToDtoList(scheduleTypeRepository.findAllByCorporationId(corId));

    }

}
