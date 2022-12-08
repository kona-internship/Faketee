package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpCorDiffException;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpDepDiffException;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpNotPermitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmpAuthValidator {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final CorporationRepository corporationRepository;

    public Employee validateCorporation(Long corId, Long usrId){
        Corporation corporation = corporationRepository.findById(corId).orElseThrow(()->new IllegalArgumentException());
        Employee employee = employeeRepository.findByUserId(usrId).orElseThrow(()->new IllegalArgumentException());

        if(!employee.getCorporation().getId().equals(corporation.getId())){
            throw new EmpCorDiffException();
        }
        return employee;
    }

    public Employee validateEmployee(EmpRole role, Employee employee){

        // 설정해놓은 권한보다 요청한 직원의 권한이 더 낮거나 직원이 활성 상태가 아닐 때
        if(role.compareTo(employee.getRole())<0 || !(employee.getVal().equals("T"))){
            throw new EmpNotPermitException();
        }
        return employee;
    }

    public void validateDepartment(Long empDepId, List<Long> requestDepIdList){

        List<DepartmentResponseDto> depList = departmentService.getAllLowDep(empDepId);

        // map으로 만들어 쓰는 것보다 containsAll이 더 빠르다
//        Map<Long, List<DepartmentResponseDto>> map =  depList.stream().collect(Collectors.toMap((dto)->dto.getId(), (dto)-> Arrays.asList(dto), (list, oneDtoList)-> {
//            list.add(oneDtoList.get(0));
//            return list;
//        }));
//        Map<Long, DepartmentResponseDto> map =  depList.stream().collect(Collectors.toMap((dto)->dto.getId(), (dto)-> dto, (old_dto, new_dto)-> {
//            return old_dto;
//        }));
//
//        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>validateDep: "+ map);
//
//        for(Long id : requestDepIdList){
//            if(!map.containsKey(id)){
//                throw new RuntimeException();
//            }
//        }

        if(!depList.containsAll(requestDepIdList)){
            throw new EmpDepDiffException();
        }

    }
}
