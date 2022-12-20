package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationGroupNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacGroupResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacGroupSaveRequestDto;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
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
    private final EmployeeService employeeService;
    private final VacInfoService vacInfoService;

    @Transactional
    public Long save(VacGroupSaveRequestDto requestDto, Long corId){

        requestDto.setCorporation(corporationRepository.findById(corId).orElseThrow(() -> new RuntimeException("No Corporation Found")));
        VacGroup vacGroup = vacGroupRepository.save(requestDto.toEntity());

        List<EmployeeResponseDto> employeeList = employeeService.getAllEmployee(corId);
        for(EmployeeResponseDto employee : employeeList){
            vacInfoService.newVacGroup(employee.getId(), vacGroup);
        }

        return vacGroup.getId();
    }

    @Transactional
    public void delete(Long id){
        // TODO: vac_info 랑 vac_type에서 vac_group을 연관관계로 가지는 데이터 삭제 후 그룹 삭제해야 함

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
