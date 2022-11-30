package com.konai.kurong.faketee.corporation.service;

import com.konai.kurong.faketee.account.entity.Users;
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

    /**
     * 회사 생성
     * 회사를 생성하는 동시에 최고 관리자 권한의 직원도 생성한다.
     *
     * @param requestDto 회사에 대한 내용
     * @return 생성된 회사의 id
     */
    @Transactional
    public Long registerCorporation(CorporationSaveRequestDto requestDto) {
        Corporation cor = requestDto.toEntity();
//        Users user = (Users) auth.getPrincipal();

        Corporation savedCor = corporationRepository.save(cor);
        /*회사 생성하면서 직원도 생성해야하는뎅ㅇ*/
        return savedCor.getId();
    }
}
