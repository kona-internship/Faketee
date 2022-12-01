package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeResponseDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleTypeSaveRequestDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleType;
import com.konai.kurong.faketee.schedule.repository.ScheduleTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleTypeRepository scheduleTypeRepository;
    private final CorporationRepository corporationRepository;

    public void registerSchType(Long corId, ScheduleTypeSaveRequestDto requestDto) {
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        ScheduleType scheduleType = ScheduleType.builder()
                .name(requestDto.getName())
                .corporation(corporation)
                .build();

        scheduleTypeRepository.save(scheduleType);
    }

    public List<ScheduleTypeResponseDto> getSchTypeList(Long corId) {
        return ScheduleTypeResponseDto.convertToDtoList(scheduleTypeRepository.findAllByCorporationId(corId));

    }

}
