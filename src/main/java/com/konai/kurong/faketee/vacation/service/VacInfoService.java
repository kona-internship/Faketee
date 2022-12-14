package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.vacation.dto.VacInfoSaveRequestDto;
import com.konai.kurong.faketee.vacation.repository.vac_info.VacInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacInfoService {

    private final VacInfoRepository vacInfoRepository;

    @Transactional
    public Long save(VacInfoSaveRequestDto requestDto){

        return vacInfoRepository.save(requestDto.toEntity()).getId();
    }

}
