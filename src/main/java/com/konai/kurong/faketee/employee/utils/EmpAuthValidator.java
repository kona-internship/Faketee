package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpCorDiffException;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpDepDiffException;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpNotPermitException;
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpUserDuplException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 권한 검증 로직을 처리하는 검증 클래스
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EmpAuthValidator {
    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final CorporationRepository corporationRepository;

    /**
     * 요청을 보낸 사용자가 요청된 회사의 직원인지 여부 확인
     *
     * @param corId 회사 id
     * @param usrId 유저 id
     * @return
     */
    public ReqEmpInfo validateCorporation(Long corId, Long usrId){
        Corporation corporation = corporationRepository.findById(corId).orElseThrow(()->new IllegalArgumentException());

        //쿼리 확인 필요
//        List<Employee> employee = employeeRepository.findByUserIdAndCorporationId(usrId, corId);
        List<Employee> employeeList = employeeRepository.getEmployeeByUserAndCorAndVal(usrId, corId, "T");
        if(employeeList.size() != 1){
            throw new EmpUserDuplException();
        }
        Employee employee = employeeList.get(0);

        if(!employee.getCorporation().getId().equals(corporation.getId())){
            throw new EmpCorDiffException();
        }
        return ReqEmpInfo.builder()
                .id(employee.getId())
                .name(employee.getName())
                .val(employee.getVal())
                .role(employee.getRole())
                .build();
    }

    /**
     * 직원이 보낸 요청에 대한 권한이 있는지 여부 확인
     *
     * @param role 권한
     * @param reqEmpInfo 요청 직원 정보 객체 (ReqEmpInfo)
     * @return
     */
    public ReqEmpInfo validateEmployee(EmpRole role, ReqEmpInfo reqEmpInfo){
        // 설정해놓은 권한보다 요청한 직원의 권한이 더 낮거나 직원이 활성 상태가 아닐 때
        if(role.compareTo(reqEmpInfo.getRole())<0 || !(reqEmpInfo.getVal().equals("T"))){
            throw new EmpNotPermitException();
        }
        return reqEmpInfo;
    }

    /**
     * 요청한 사람이 속해 있는 조직의 하위 조직에 접근하는 요청인지 여부 확인
     *
     * @param empDepId
     * @param requestDepIdList
     */
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
