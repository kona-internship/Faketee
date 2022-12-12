package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.vacation.dto.VacGroupSaveRequestDto;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.repository.VacGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacGroupService {

    private final VacGroupRepository vacGroupRepository;

    @Transactional
    public Long save(VacGroupSaveRequestDto requestDto){

        return vacGroupRepository.save(requestDto.toEntity()).getId();
    }

    public void delete(Long id){

        vacGroupRepository.deleteById(id);
    }
}
