package com.konai.kurong.faketee.employee.service;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.account.service.EmailAuthService;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.employee.dto.*;
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

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeInfoRepository employeeInfoRepository;
    private final EmailAuthService emailAuthService;
    private final CorporationRepository corporationRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    /**
     * 직원 저장하기
     * @param corId
     * @param requestDto
     */
    @Transactional
    public void registerEmployee(Long corId, EmployeeSaveRequestDto requestDto) {
//        해당 회사의 관리자인지 권한 확인 필요

//        합류코드 만들기
        String joinCode = emailAuthService.createJoinCode();

        // 합류코드 있는지 여부 검증
        Integer count = 0;
        while(employeeInfoRepository.findByJoinCode(joinCode).isPresent()){
            if(count>100){
                throw new RuntimeException();
            }
            count++;
        }

//        직원 상세정보(EMP_INFO) Entity 만들어서 먼저 저장하기
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .joinDate(new java.sql.Timestamp(requestDto.getJoinDate().getTime()).toLocalDateTime())
                .freeDate(new java.sql.Timestamp(requestDto.getJoinDate().getTime()).toLocalDateTime())
                .major(requestDto.getMajor())
                .cert(requestDto.getCert())
                .info(requestDto.getInfo())
                .empNo(requestDto.getEmpNo())
                .email(requestDto.getEmail())
                .joinCode(joinCode)
                .build();
        
        employeeInfoRepository.save(employeeInfo);

//        회사 합류코드 전송하기
        emailAuthService.sendJoinCode(employeeInfo.getEmail(), joinCode);

//        직원 만들기에 필요한 것들 불러오기
        EmpRole role = EmpRole.valueOf(requestDto.getRole());
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();
        Position position = positionRepository.findById(requestDto.getPositionId()).orElseThrow();
        Department department = departmentRepository.findById(requestDto.getDepartmentId()).orElseThrow();

//       직원(EMP) Entity 만들기
        Employee employee = Employee.builder()
                .name(requestDto.getName())
                .role(role)
                .val("W")   //아직 합류코드 인증 전이므로 무조건 W
                .corporation(corporation)
                .user(null) //사용자가 합류코드 인증했을 때 userId 넣어줄거야
                .position(position)
                .department(department)
                .employeeInfo(employeeInfo)
                .build();

//        직원(EMP) Entity 저장하기
        employeeRepository.save(employee);
    }

    /**
     * 직원 수정하기
     * @param corId
     * @param employeeId
     * @param requestDto
     */
    @Transactional
    public void updateEmployee(Long corId, Long employeeId, EmployeeUpdateRequestDto requestDto) {
//        해당 회사의 관리자인지 권한 확인 필요

        //        직원 상세정보(EMP_INFO) Entity 만들어서 먼저 수정하기
        EmployeeInfo oldEmployeeInfo = findByEmployeeById(employeeId).getEmployeeInfo();

        EmployeeInfo newEmployeeInfo = EmployeeInfo.builder()
                .joinDate(new java.sql.Timestamp(requestDto.getJoinDate().getTime()).toLocalDateTime())
                .freeDate(new java.sql.Timestamp(requestDto.getJoinDate().getTime()).toLocalDateTime())
                .major(requestDto.getMajor())
                .cert(requestDto.getCert())
                .info(requestDto.getInfo())
                .empNo(requestDto.getEmpNo())
                .build();

        employeeInfoRepository.findById(oldEmployeeInfo.getId()).orElseThrow().update(newEmployeeInfo);

//        직원 수정에 필요한 것들 불러오기
        EmpRole role = EmpRole.valueOf(requestDto.getRole());
        Position position = positionRepository.findById(requestDto.getPositionId()).orElseThrow();
        Department department = departmentRepository.findById(requestDto.getDepartmentId()).orElseThrow();

//        수정한 직원(EMP) Entity 만들기
        Employee employee = Employee.builder()
                .name(requestDto.getName())
                .role(role)
                .position(position)
                .department(department)
                .build();

        //        직원(EMP) Entity 수정하기
        employeeRepository.findById(employeeId).orElseThrow().update(employee);
    }

    /**
     * 직원 합류
     *
     */
    @Transactional
    public void joinEmployee(Long userId, EmployeeJoinRequestDto requestDto){
        EmployeeInfo employeeInfo = employeeInfoRepository.findByJoinCode(requestDto.getJoinCode()).orElseThrow(()->new IllegalArgumentException());
        Employee employee = employeeRepository.findByEmployeeInfoId(employeeInfo.getId()).orElseThrow(()->new IllegalArgumentException());
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException());

        if(employee.getVal().equals("T")){
            throw new RuntimeException();
        }
        if(!requestDto.getJoinCode().equals(employee.getEmployeeInfo().getJoinCode())){
            throw new RuntimeException();
        }
        employee.join(user);
    }

    public List<EmployeeResponseDto> getAllEmployee(Long corId) {
//        회사의 모든 직원을 볼 수 있는 권한은 생각해 볼 필요 있음
//        전직원이 가능한지 / 관리자들만 가능한지

        List<Employee> employees = employeeRepository.findByCorporationId(corId);
        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();
        for(Employee employee : employees) {
            employeeResponseDtos.add(getEmployeeResponseDto(employee.getId()));
        }
        return employeeResponseDtos;
    }

    /**
     * 직원 비활성화
     * @param corId
     * @param employeeId
     */
    @Transactional
    public void deactivateEmployee(Long corId, Long employeeId) {
//        해당 회사의 관리자인지 권한 확인 필요

        Employee employee = findByEmployeeById(employeeId);
        employee.deactivate();
    }

    /**
     * 직원 합류 초대 재전송
     * @param corId
     * @param employeeId
     * @param requestDto
     */
    public void reSendJoinCode(Long corId, Long employeeId, EmployeeReSendRequestDto requestDto) {
//        해당 회사의 관리자인지 권한 확인 필요

        Employee employee = findByEmployeeById(employeeId);
        EmployeeInfo employeeInfo = findByEmployeeInfoById(employee.getEmployeeInfo().getId());
        emailAuthService.sendJoinCode(requestDto.getEmail(), employeeInfo.getJoinCode());
    }

    /**
     * employeeId 로 Employee 반환
     * @param employeeId
     * @return
     */
    public Employee findByEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow();
    }

    /**
     * employeeId로 Employee 반환
     * @param employeeInfoId
     * @return
     */
    public EmployeeInfo findByEmployeeInfoById(Long employeeInfoId) {
        return employeeInfoRepository.findById(employeeInfoId).orElseThrow();
    }

    /**
     * 직원 responseDto 만들기
     * @param employeeId
     * @return
     */
    public EmployeeResponseDto getEmployeeResponseDto(Long employeeId) {
        Employee employee = findByEmployeeById(employeeId);
        EmployeeInfo employeeInfo = findByEmployeeInfoById(employee.getEmployeeInfo().getId());
        EmpRole role = employee.getRole();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        EmployeeResponseDto employeeResponseDto = EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .role(role.getRole())
                .corporationId(employee.getCorporation().getId())
                .positionId(employee.getPosition().getId())
                .departmentId(employee.getDepartment().getId())
                .joinDate(simpleDateFormat.format(java.sql.Timestamp.valueOf(employeeInfo.getJoinDate())))
                .freeDate(simpleDateFormat.format(java.sql.Timestamp.valueOf(employeeInfo.getFreeDate())))
                .empNo(employeeInfo.getEmpNo())
                .major(employeeInfo.getMajor())
                .cert(employeeInfo.getCert())
                .info(employeeInfo.getInfo())
                .val(employee.getVal())
                .build();

        return employeeResponseDto;
    }
}
