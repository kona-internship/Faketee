package com.konai.kurong.faketee.employee.utils;

import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpDepDiffException;
import com.konai.kurong.faketee.utils.exception.custom.empauth.EmpNotPermitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmpAuthParamValidator {
    private final DepartmentService departmentService;
    private final DraftRepository draftRepository;

    /**
     * 요청한 사람이 속해 있는 조직의 하위 조직에 접근하는 요청인지 여부 확인
     *
     * @param empDepId
     * @param requestDepIdList
     */
    public void validateDepartment(Long empDepId, List<Long> requestDepIdList){

        List<DepartmentResponseDto> depList = departmentService.getAllLowDep(empDepId);

        if(!depList.stream().map(dto->dto.getId()).collect(Collectors.toSet()).containsAll(requestDepIdList)){
            throw new EmpDepDiffException();
        }
    }

    public void validateDraft(Long empDepId, List<Long> requestDraftIdList){

        // 벌크로 나가는지 한개씩 나가는지 쿼리확인 필요
        List<Long> draftIdList = draftRepository.getDraftIdsByEmployeeId(empDepId);

        if(!draftIdList.containsAll(requestDraftIdList)){
            throw new EmpNotPermitException();
        }
    }
}
