package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationInfoNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacInfoResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacInfoSaveRequestDto;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.entity.VacInfo;
import com.konai.kurong.faketee.vacation.repository.vac_group.VacGroupRepository;
import com.konai.kurong.faketee.vacation.repository.vac_info.VacInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacInfoService {

    private final VacInfoRepository vacInfoRepository;
    private final VacGroupRepository vacGroupRepository;

    @Transactional
    public Long save(VacInfoSaveRequestDto requestDto){

        return vacInfoRepository.save(requestDto.toEntity()).getId();
    }

    public List<VacInfoResponseDto> loadByEmpId(Long empId){

        return vacInfoRepository.findAllByEmpId(empId)
                .stream()
                .map(VacInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<VacInfoResponseDto> loadByCorId(Long corId){

        return vacInfoRepository.findAllByCorId(corId)
                .stream()
                .map(VacInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<VacInfoResponseDto> loadByDepId(Long depId){

        return vacInfoRepository.findAllByDepId(depId)
                .stream()
                .map(VacInfoResponseDto::new)
                .collect(Collectors.toList());
    }

    public VacInfoResponseDto findById(Long infoId){

        return new VacInfoResponseDto(vacInfoRepository.findById(infoId).orElseThrow(VacationInfoNotFoundException::new));
    }

    public void addVacationInfo(Employee employee, Long corId){

        for(VacGroup group : vacGroupRepository.findAllByCorId(corId)){
                    this.save(VacInfoSaveRequestDto.builder()
                    .remaining(0D)
                    .employee(employee)
                    .vacGroup(group)
                    .build());
        }
    }
}
