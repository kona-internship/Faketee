package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftCrudType;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import com.konai.kurong.faketee.employee.repository.EmployeeInfoRepository;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.position.repository.PositionRepository;
import com.konai.kurong.faketee.vacation.entity.VacGroup;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.konai.kurong.faketee.vacation.entity.VacType;
import com.konai.kurong.faketee.vacation.repository.VacRequestRepository;
import com.konai.kurong.faketee.vacation.repository.VacGroupRepository;
import com.konai.kurong.faketee.vacation.repository.VacTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@SpringBootTest
class QuerydslDraftRepositoryImplTest {

    @Autowired
    private DraftRepository draftRepository;
    @Autowired
    private CorporationRepository corporationRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private EmployeeInfoRepository employeeInfoRepository;
    @Autowired
    private VacRequestRepository vacDateRequestRepository;
    @Autowired
    private VacGroupRepository vacGroupRepository;
    @Autowired
    private VacTypeRepository vacTypeRepository;

    @Test
    void getDraftsWithRequestsByApproverAndStateCode() {
        User user = userRepository.findById(1L).get();
        Corporation cor = Corporation.builder()
                .name("test_cor")
                .build();
        cor.setCRE_ID(1L);
        cor.setCRE_DTTM(LocalDateTime.now());
        Corporation savedCor = corporationRepository.save(cor);
        Department department = Department.builder()
                .name("인사팀")
                .level(0L)
                .corporation(savedCor)
                .build();
        department.setCRE_ID(1L);
        department.setCRE_DTTM(LocalDateTime.now());
        Department saveDep = departmentRepository.save(department);
        Position position = Position.builder()
                .name("CEO")
                .corporation(savedCor)
                .build();
        position.setCRE_ID(1L);
        position.setCRE_DTTM(LocalDateTime.now());
        Position savePos = positionRepository.save(position);
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .email("test2@test.com")
                .joinCode("admin2")
                .build();
        employeeInfo.setCRE_ID(1L);
        employeeInfo.setCRE_DTTM(LocalDateTime.now());
        EmployeeInfo saveEmp = employeeInfoRepository.save(employeeInfo);
        Employee employee = Employee.builder()
                .user(user)
                .department(saveDep)
                .name("test_emp")
                .role(EmpRole.ADMIN)
                .val("T")
                .employeeInfo(saveEmp)
                .corporation(savedCor)
                .position(savePos)
                .build();
        employee.setCRE_ID(1L);
        employee.setCRE_DTTM(LocalDateTime.now());
        Employee sEmp = employeeRepository.save(employee);
        VacGroup vacGroup = VacGroup.builder()
                .name("test_vac_group")
                .corporation(savedCor)
                .approvalLevel("1")
                .build();
        vacGroup.setCRE_ID(1L);
        vacGroup.setCRE_DTTM(LocalDateTime.now());
        vacGroupRepository.save(vacGroup);
        VacType vacType = VacType.builder()
                .name("test_vac_type")
                .vacGroup(vacGroup)
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .sub(3.)
                .build();
        vacType.setCRE_ID(1L);
        vacType.setCRE_DTTM(LocalDateTime.now());
        vacTypeRepository.save(vacType);
        for(int i=0; i<10;i++) {
            Draft draft = Draft.builder()
                    .stateCode(DraftStateCode.WAIT)
                    .requestEmployee(sEmp)
                    .requestMessage("test_rm")
                    .requestType(DraftRequestType.VACATION)
                    .crudType(DraftCrudType.CREATE)
                    .requestDate(LocalDateTime.now())
                    .approvalEmpFin(sEmp)
                    .build();
            draft.setCRE_ID(1L);
            draft.setCRE_DTTM(LocalDateTime.now());
            Draft saveDraft = draftRepository.save(draft);
            for(int j=0; j<10;j++) {
                VacRequest vacDateRequest = VacRequest.builder()
                        .date(LocalDateTime.now())
                        .vacType(vacType)
                        .draft(saveDraft)
                        .employee(sEmp)
                        .val("T")
                        .build();
                vacDateRequest.setCRE_ID(1L);
                vacDateRequest.setCRE_DTTM(LocalDateTime.now());
                VacRequest saveVac = vacDateRequestRepository.save(vacDateRequest);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+saveVac);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>"+vacDateRequest.getEmployee().getId());
            }
        }



        List<Draft> draftList = draftRepository.getDraftsWithRequestsByApproverAndStateCode(sEmp.getId(), DraftStateCode.getWaitingForApproval());

        assertThat(draftList.isEmpty(), equalTo(true));
    }

    @Test
    void getDraftsWithRequestsByEmployeeAndStateCode() {
    }

    @Test
    void updateDraftStateCode() {
    }

    @Test
    void getDraftIdsByEmployeeId() {
    }
}