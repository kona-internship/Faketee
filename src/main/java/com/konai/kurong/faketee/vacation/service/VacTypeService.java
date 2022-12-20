package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationGroupNotFoundException;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationTypeNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacTypeSaveRequestDto;
import com.konai.kurong.faketee.vacation.repository.vac_type.VacTypeRepository;
import com.konai.kurong.faketee.vacation.repository.vac_group.VacGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacTypeService {

    private final VacTypeRepository vacTypeRepository;
    private final VacGroupRepository vacGroupRepository;

    @Transactional
    public Long save(VacTypeSaveRequestDto requestDto, Long vacGroupId){

        requestDto.setVacGroup(vacGroupRepository.findById(vacGroupId).orElseThrow(VacationGroupNotFoundException::new));
        return vacTypeRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id){

        vacTypeRepository.deleteById(id);
    }

    public List<VacTypeResponseDto> loadByCorId(Long corId){

        return vacTypeRepository.findAllByCorId(corId)
                .stream()
                .map(VacTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<VacTypeResponseDto> loadByVacGroupId(Long vacGroupId){

        return vacTypeRepository.findAllByVacGroupId(vacGroupId)
                .stream()
                .map(VacTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    public VacTypeResponseDto findById(Long typeId){

        return new VacTypeResponseDto(vacTypeRepository.findById(typeId).orElseThrow(VacationTypeNotFoundException::new));
    }

}
