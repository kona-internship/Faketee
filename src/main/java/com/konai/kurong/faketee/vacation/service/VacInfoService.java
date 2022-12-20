package com.konai.kurong.faketee.vacation.service;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationInfoNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacInfoResponseDto;
import com.konai.kurong.faketee.vacation.dto.VacInfoSaveRequestDto;
import com.konai.kurong.faketee.vacation.dto.VacInfoUpdateRequestDto;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
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
    private final EmployeeRepository employeeRepository;

    /**
     * 휴가 정보 생성
     *
     * @param requestDto
     * @return
     */
    @Transactional
    public Long save(VacInfoSaveRequestDto requestDto){

        return vacInfoRepository.save(requestDto.toEntity()).getId();
    }

    /**
     * 직원이 가지고 있는 휴가 정보(들)을 반환한다.
     *
     * @param empId
     * @return
     */
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

    /**
     * 휴가 정보를 반환한다.
     *
     * @param infoId
     * @return
     */
    public VacInfoResponseDto findById(Long infoId){

        return new VacInfoResponseDto(vacInfoRepository.findById(infoId).orElseThrow(VacationInfoNotFoundException::new));
    }

    /**
     * 관리자가 직원을 등록할 시 작동한다.
     *
     * 직원이 등록될 때 회사에 존재하는 VacGroup 이 해당 직원의 VacInfo 에 추가된다.
     * @param employee
     * @param corId
     */
    @Transactional
    public void initVacInfo(Employee employee, Long corId){

        for(VacGroup group : vacGroupRepository.findAllByCorId(corId)){
                    this.save(VacInfoSaveRequestDto.builder()
                    .remaining(0D)
                    .employee(employee)
                    .vacGroup(group)
                    .build());
        }
    }

    /**
     * 관리자가 새로운 휴가그룹을 추가할 시 작동된다.
     *
     * 새롭게 추가된 VacGroup 은 해당 회사의 직원들의 VacInfo 에 추가된다.
     * @param empId
     * @param vacGroup
     */
    @Transactional
    public void newVacGroup(Long empId, VacGroup vacGroup){

        this.save(VacInfoSaveRequestDto.builder()
                .remaining(0D)
                .employee(employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee Not Found")))
                .vacGroup(vacGroup)
                .build());
    }

    /**
     * 관리자가 휴가 부여를 시행할때 작동된다.
     *
     * 관리자가 선택한 직원의 VacInfo 에서 Remaining (잔여일수) 를 업데이트한다.
     * @param requestDto
     * @return
     */
    @Transactional
    public Long updateInfo(VacInfoUpdateRequestDto requestDto){

        return vacInfoRepository.updateByEmpAndVacGroupId(requestDto.getEmpId(), requestDto.getVacGroupId()).updateVacInfo(requestDto);
    }

}
