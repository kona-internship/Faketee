package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationGroupNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacGroupResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacGroupSaveRequestDto;
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
public class VacGroupService {

    private final VacGroupRepository vacGroupRepository;
    private final CorporationRepository corporationRepository;

    @Transactional
    public Long save(VacGroupSaveRequestDto requestDto, Long corId){

        requestDto.setCorporation(corporationRepository.findById(corId).orElseThrow(() -> new RuntimeException("No Corporation Found")));
        return vacGroupRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id){

        vacGroupRepository.deleteById(id);
    }

    public List<VacGroupResponseDto> loadVacGroups(Long corId){

        return vacGroupRepository.findAllByCorId(corId)
                .stream()
                .map(VacGroupResponseDto::new)
                .collect(Collectors.toList());
    }

    public VacGroupResponseDto findById(Long groupId){

        return new VacGroupResponseDto(vacGroupRepository.findById(groupId).orElseThrow(VacationGroupNotFoundException::new));
    }

}
