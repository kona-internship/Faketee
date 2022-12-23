package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationGroupNotFoundException;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationTypeNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacTypeResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacTypeSaveRequestDto;
import com.konai.kurong.faketee.vacation.repository.VacTypeRepository;
import com.konai.kurong.faketee.vacation.repository.VacGroupRepository;
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

    /**
     * 휴가 유형 생성
     *
     * 관리자가 새로운 휴가 유형을 생성할 때 호출 (VacTypeApiController)
     * @param requestDto : requestDto
     * @param vacGroupId : 휴가그룹의 key 값
     * @return
     */
    @Transactional
    public Long save(VacTypeSaveRequestDto requestDto, Long vacGroupId){

        requestDto.setVacGroup(vacGroupRepository.findById(vacGroupId).orElseThrow(VacationGroupNotFoundException::new));
        return vacTypeRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 휴가 유형 삭제
     *
     * @param id : 휴가유형의 key 값
     */
    @Transactional
    public void delete(Long id){

        vacTypeRepository.deleteById(id);
    }

    /**
     * VacGroup 에 해당하는 휴가 유형(들)을 삭제
     *
     * @param vacGroupId
     */
    @Transactional
    public void deleteByVacGroupId(Long vacGroupId){

        vacTypeRepository.deleteByVacGroupId(vacGroupId);
    }

    /**
     * 회사의 휴가 유형(들)을 반환한다.
     *
     * URL 에 있는 corId 를 사용하여 해당 corporation 의 vac type 들을 찾는다.
     *
     * @param corId : URL 의 PathVariable
     * @return
     */
    public List<VacTypeResponseDto> loadByCorId(Long corId){

        return vacTypeRepository.findAllByCorId(corId)
                .stream()
                .map(VacTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 휴가 그룹에 속해 있는 휴가 유형(들)을 반환한다.
     *
     * @param vacGroupId : 휴가그룹의 key 값
     * @return
     */
    public List<VacTypeResponseDto> loadByVacGroupId(Long vacGroupId){

        return vacTypeRepository.findAllByVacGroupId(vacGroupId)
                .stream()
                .map(VacTypeResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 휴가유형을 반환한다.
     *
     * @param typeId: 휴가유형의 key 값
     * @return
     */
    public VacTypeResponseDto findById(Long typeId){

        return new VacTypeResponseDto(vacTypeRepository.findById(typeId).orElseThrow(VacationTypeNotFoundException::new));
    }

}
