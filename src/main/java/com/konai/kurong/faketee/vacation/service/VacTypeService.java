package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationGroupNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacTypeSaveRequestDto;
import com.konai.kurong.faketee.vacation.repository.vac_type.VacTypeRepository;
import com.konai.kurong.faketee.vacation.repository.vac_group.VacGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacTypeService {

    private final VacTypeRepository vacTypeRepository;
    private final VacGroupRepository vacGroupRepository;

    @Transactional
    public Long save(VacTypeSaveRequestDto requestDto, Long vacGroupId){

        requestDto.setVacGroup(vacGroupRepository.findById(vacGroupId).orElseThrow(() -> new VacationGroupNotFoundException()));
        return vacTypeRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id){

        vacTypeRepository.deleteById(id);
    }

    public List<VacTypeResponseDto> loadVacTypesByCorId(Long corId){

        return vacTypeRepository.findAllByCorId(corId);
    }

    public List<VacTypeResponseDto> loadVacTypesByVacGroupId(Long vacGroupId){

        return vacTypeRepository.findAllByVacGroupId(vacGroupId);
    }

}
