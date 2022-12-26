package com.konai.kurong.faketee.vacation.service;


import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.draft.utils.DraftCrudType;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.utils.exception.custom.vacation.VacationTypeNotFoundException;
import com.konai.kurong.faketee.vacation.dto.VacRequestFormDto;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.konai.kurong.faketee.vacation.repository.VacRequestRepository;
import com.konai.kurong.faketee.vacation.repository.VacTypeRepository;
import com.konai.kurong.faketee.vacation.util.RequestVal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacRequestService {

    private final VacTypeRepository vacTypeRepository;
    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final DraftRepository draftRepository;
    private final VacRequestRepository vacRequestRepository;
    private final DepartmentService departmentService;

    public static final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<EmployeeResponseDto> findApprovalLine(Long corId, Long empID) {

        return employeeService.findApprovalLine(corId, employeeService.findById(empID).getDepartmentId());
    }

    @Transactional
    public Long newVacationRequest(VacRequestFormDto requestFormDto, Long requestEmpId) {

        Employee requestEmployee = employeeRepository.findById(requestEmpId).orElseThrow(() -> new RuntimeException("No Employee Found"));
        List<EmployeeResponseDto> approvalEmployees = employeeService.findApprovalsById(requestFormDto.getApprovals());
        Draft newDraft = createDraft(requestEmployee, alignApprovals(approvalEmployees), requestFormDto, DraftCrudType.CREATE);

        for (String date : requestFormDto.getDates()){
            vacRequestRepository.save(VacRequest.builder()
                    .date(LocalDate.parse(date, DATETIMEFORMATTER))
                    .originStartTime(null)
                    .val(RequestVal.T)
                    .vacType(vacTypeRepository.findById(requestFormDto.getVacTypeId()).orElseThrow(VacationTypeNotFoundException::new))
                    .draft(newDraft)
                    .employee(requestEmployee)
                    .build());
        }
        return newDraft.getId();
    }

    @Transactional
    public Draft createDraft(Employee requestEmployee, List<Employee> approvals, VacRequestFormDto requestFormDto, DraftCrudType crudType) {

        if (approvals.size() == 1) {
            Draft draft = Draft.builder()
                    .approvalDate(null)
                    .approvalMessage(null)
                    .requestDate(LocalDateTime.now())
                    .stateCode(DraftStateCode.WAIT)
                    .requestType(DraftRequestType.VACATION)
                    .requestMessage(requestFormDto.getRequestMessage())
                    .crudType(crudType)
                    .requestEmployee(requestEmployee)
                    .approvalEmpFin(approvals.get(0))
                    .build();
            return draftRepository.save(draft);
        } else if (approvals.size() == 2) {
            Draft draft = Draft.builder()
                    .approvalDate(null)
                    .approvalMessage(null)
                    .requestDate(LocalDateTime.now())
                    .stateCode(DraftStateCode.WAIT)
                    .requestType(DraftRequestType.VACATION)
                    .requestMessage(requestFormDto.getRequestMessage())
                    .crudType(crudType)
                    .requestEmployee(requestEmployee)
                    .approvalEmp1(approvals.get(0))
                    .approvalEmpFin(approvals.get(1))
                    .build();
            return draftRepository.save(draft);
        } else {
            throw new RuntimeException("Error creating draft in Vacation Request");
        }
    }

    @Transactional
    public List<Employee> alignApprovals(List<EmployeeResponseDto> approvals) {

        if (approvals.isEmpty())
            return null;

        if (approvals.size() == 1){
            List<Employee> approval = new ArrayList<>();
            approval.add(employeeService.findEntityById(approvals.get(0).getId()));
            return approval;
        }

        List<Employee> alignApprovals = new ArrayList<>();
        Long approval1DepLevel = DepartmentResponseDto.convertToDto(departmentService.findById(approvals.get(0).getDepartmentId())).getLevel();
        Long approval2DepLevel = DepartmentResponseDto.convertToDto(departmentService.findById(approvals.get(1).getDepartmentId())).getLevel();
        if (approval1DepLevel < approval2DepLevel) {
            alignApprovals.add(employeeService.findEntityById(approvals.get(0).getId()));
            alignApprovals.add(employeeService.findEntityById(approvals.get(1).getId()));
        } else if (Objects.equals(approval1DepLevel, approval2DepLevel)){
            Employee approval1 = employeeService.findEntityById(approvals.get(0).getId());
            Employee approval2 = employeeService.findEntityById(approvals.get(1).getId());
            if (approval1.getRole() == EmpRole.ADMIN){
                alignApprovals.add(approval1);
                alignApprovals.add(approval2);
            }else if(approval2.getRole() == EmpRole.ADMIN){
                alignApprovals.add(approval2);
                alignApprovals.add(approval1);
            }else
                throw new RuntimeException("Error creating aligned approvals");
        }
        else {
            alignApprovals.add(employeeService.findEntityById(approvals.get(1).getId()));
            alignApprovals.add(employeeService.findEntityById(approvals.get(0).getId()));
        }
        return alignApprovals;
    }
}

