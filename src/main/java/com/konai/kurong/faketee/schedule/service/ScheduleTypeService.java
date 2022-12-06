package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeResponseDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.repository.schedule.ScheduleTypeRepository;
import com.konai.kurong.faketee.schedule.repository.template.TemplateRepository;
import com.konai.kurong.faketee.utils.exception.custom.Schedule.ConnectedTmpExistException;
import com.konai.kurong.faketee.utils.exception.custom.Schedule.ScheduleTypeNotFoundException;
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
            throw new ConnectedTmpExistException();
        }
        //아무도 참조하지 않으므로 삭제 가능
        scheduleTypeRepository.deleteById(typeId);
    }
    public List<ScheduleTypeResponseDto> getSchTypeList(Long corId) {
        return ScheduleTypeResponseDto.convertToDtoList(scheduleTypeRepository.findAllByCorporationId(corId));

    }

    public ScheduleType findById(Long id){

        return scheduleTypeRepository.findById(id).orElseThrow(() -> new ScheduleTypeNotFoundException());
    }

}
