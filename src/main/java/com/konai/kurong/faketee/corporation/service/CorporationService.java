package com.konai.kurong.faketee.corporation.service;

import com.konai.kurong.faketee.corporation.dto.CorporationSaveRequestDto;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporationService {
    private final CorporationRepository corporationRepository;

    /*회사 생성*/
    @Transactional
    public Long registerCorporation(CorporationSaveRequestDto requestDto) {
        Corporation cor = requestDto.toEntity();

        Corporation savedCor = corporationRepository.save(cor);
        return savedCor.getId();
    }
    /*회사 가져오기*/
}
