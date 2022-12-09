package com.konai.kurong.faketee.corporation.service;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.corporation.dto.CorporationResponseDto;
import com.konai.kurong.faketee.corporation.dto.CorporationSaveRequestDto;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.entity.EmployeeInfo;
import com.konai.kurong.faketee.employee.repository.EmployeeInfoRepository;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.position.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporationService {
    private final CorporationRepository corporationRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final EmployeeInfoRepository employeeInfoRepository;

    /**
     * 회사 생성
     * 회사를 생성하는 동시에 최고 관리자 권한의 직원도 생성한다.
     *
     * @param requestDto 회사에 대한 내용
     * @return 생성된 회사의 id
     */
    @Transactional
    public Long registerCorporation(CorporationSaveRequestDto requestDto,
                                    Long userId,
                                    String userName,
                                    String userEmail) {
        Corporation cor = requestDto.toEntity();
        Corporation savedCor = corporationRepository.save(cor);

        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException());
        Department department = Department.builder()
                .name("인사팀")
                .level(0L)
                .corporation(savedCor)
                .build();
        Department saveDep = departmentRepository.save(department);
        Position position = Position.builder()
                .name("CEO")
                .corporation(savedCor)
                .build();
        Position savePos = positionRepository.save(position);
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .email(userEmail)
                .joinCode("admin")
                .build();
        EmployeeInfo saveEmp = employeeInfoRepository.save(employeeInfo);

        Employee employee = Employee.builder()
                .user(user)
                .department(saveDep)
                .name(userName)
                .role(EmpRole.ADMIN)
                .val("T")
                .employeeInfo(saveEmp)
                .corporation(savedCor)
                .position(savePos)
                .build();
        employeeRepository.save(employee);

        return savedCor.getId();
    }

    public CorporationResponseDto findById(Long corId){

        return CorporationResponseDto.convertToDto(corporationRepository.findById(corId).orElseThrow(() -> new IllegalArgumentException()));
    }
}