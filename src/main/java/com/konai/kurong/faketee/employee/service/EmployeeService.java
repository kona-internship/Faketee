package com.konai.kurong.faketee.employee.service;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.account.repository.UserRepository;
import com.konai.kurong.faketee.account.service.EmailAuthService;
import com.konai.kurong.faketee.auth.dto.SessionUser;
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
import com.konai.kurong.faketee.utils.exception.custom.employee.EmpUserDuplException;
import com.konai.kurong.faketee.vacation.service.VacInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeInfoRepository employeeInfoRepository;
    private final EmailAuthService emailAuthService;
    private final VacInfoService vacInfoService;
    private final CorporationRepository corporationRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    /**
     * 직원 등록하기
     * @param corId
     * @param requestDto
     */
    @Transactional
    public void registerEmployee(Long corId, EmployeeSaveRequestDto requestDto) {
//        합류코드 만들기
        String joinCode = emailAuthService.createJoinCode();

        // 합류코드 있는지 여부 검증
        Integer count = 0;
        while(employeeInfoRepository.findByJoinCode(joinCode).isPresent()){
            if(count>5){
                throw new RuntimeException();
            }
            count++;
        }

//        직원 상세정보(EMP_INFO) Entity 만들어서 먼저 저장하기
        EmployeeInfo employeeInfo = EmployeeInfo.builder()
                .joinDate(new java.sql.Timestamp(requestDto.getJoinDate().getTime()).toLocalDateTime())
                .freeDate(new java.sql.Timestamp(requestDto.getFreeDate().getTime()).toLocalDateTime())
                .major(requestDto.getMajor())
                .cert(requestDto.getCert())
                .info(requestDto.getInfo())
                .empNo(requestDto.getEmpNo())
                .email(requestDto.getEmail())
                .joinCode(joinCode)
                .build();
        log.info(">>>>>>>>>>>>>>>>>"+employeeInfo);
        employeeInfoRepository.save(employeeInfo);

//        회사 합류코드 전송하기
        emailAuthService.sendJoinCode(employeeInfo.getEmail(), joinCode);

//        직원 만들기에 필요한 것들 불러오기
        EmpRole role = EmpRole.convertToType(requestDto.getRole());
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
        vacInfoService.initVacInfo(employee, corId);
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
        EmpRole role = EmpRole.convertToType(requestDto.getRole());
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
     * @param userId
     * @param requestDto
     */
    @Transactional
    public Long joinEmployee(Long userId, EmployeeJoinRequestDto requestDto, SessionUser sessionUser, HttpServletRequest httpServletRequest){
        EmployeeInfo employeeInfo = employeeInfoRepository.findByJoinCode(requestDto.getJoinCode()).orElseThrow(()->new IllegalArgumentException());
        Employee employee = employeeRepository.findByEmployeeInfoId(employeeInfo.getId()).orElseThrow(()->new IllegalArgumentException());
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException());

        // 회사에 현재 같은 유저 아이디를 가지고 있는 직원이 없는지 확인
        List<Employee> employeeList = employeeRepository.getEmployeeByUserAndCorAndVal(null, employee.getCorporation().getId(), "W");
        if(employeeList.size() != 1){
            throw new EmpUserDuplException();
        }

        // 요청의 합류코드와 디비에 들어있는 합류코드와 일치하는지 확인
        if(!requestDto.getJoinCode().equals(employee.getEmployeeInfo().getJoinCode())){
            throw new RuntimeException();
        }
        employee.join(user);
        sessionUser.setEmployeeIdList(convertToSessionDto(findByUserId(sessionUser.getId())));
        httpServletRequest.getSession().setAttribute("user", sessionUser);
        return employee.getCorporation().getId();
    }

    /**
     * 회사의 직원 목록 불러오기
     * @param corId
     * @return
     */
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
//        EmployeeResponseDto 만들기에 필요한 것들 불러오기
        Employee employee = findByEmployeeById(employeeId);
        EmployeeInfo employeeInfo = employee.getEmployeeInfo();
        EmpRole role = employee.getRole();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

//        EmployeeResponseDto 만들기
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .role(role.getRole())
                .corporationId(employee.getCorporation().getId())

                .positionId((employee.getPosition().getId() == null) ? null : employee.getPosition().getId())
                .departmentId((employee.getDepartment().getId() == null) ? null : employee.getDepartment().getId())
                .joinDate((employeeInfo.getJoinDate() != null) ? simpleDateFormat.format(java.sql.Timestamp.valueOf(employeeInfo.getJoinDate())) : null)
                .freeDate((employeeInfo.getFreeDate() != null) ? simpleDateFormat.format(java.sql.Timestamp.valueOf(employeeInfo.getFreeDate())) : null)
                .empNo((employeeInfo.getEmpNo() != null) ? employeeInfo.getEmpNo() : null)
                .major((employeeInfo.getMajor() != null) ? employeeInfo.getMajor() : null)
                .cert((employeeInfo.getCert() != null) ? employeeInfo.getCert() : null)
                .info((employeeInfo.getInfo() != null) ? employeeInfo.getInfo() : null)

                .val(employee.getVal())
                .build();
    }

    /**
     * 조직리스트와 직무리스트를 매개변수로 받아
     * 모두 해당되는 직원의 리스트를 반환한다.
     *
     * @param depIds
     * @param posIds
     * @return
     */
    @Transactional
    public List<EmployeeSchResponseDto> getEmpByDepAndPos(List<Long> depIds, List<Long> posIds){
        List<Employee> emp = employeeRepository.getEmployeeByDepAndPos(depIds, posIds);

        List<EmployeeSchResponseDto> dtoList = EmployeeSchResponseDto.convertToDtoList(emp);
        return dtoList;
    }

    public List<EmployeeResponseDto> findByDepId(Long depId){

        return employeeRepository.findByDepId(depId)
                .stream()
                .map(EmployeeResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<EmployeeResponseDto> findByUserId(Long userId){

        return employeeRepository.findByUserId(userId)
                .stream()
                .map(EmployeeResponseDto::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EmployeeSessionResponseDto> convertToSessionDto(List<EmployeeResponseDto> list){

        return list
                .stream()
                .map(EmployeeSessionResponseDto::convertToSessionDto)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDto findById(Long empId){

        return EmployeeResponseDto.convertToDto(employeeRepository.findById(empId).orElseThrow());
    }

    /**
     * 출퇴근 기록 승인권자 가져오기
     * 1. empId의 role이 직원일 경우
     * 소속되어있는 dept의 관리자부터 가져온다. 그 후로 소속된 DEPT 의 상위 DEPT 의 상위관리자(들을 계속해서 가져온다
     *
     * 2. empId의 role이 직원이 아닐 경우(조직관리자, 총괄관리자)
     * 소속되어있는 dept의 상위 dept의 상위 관리자부터 가져온다.(조직관리자 - 총괄관리자에게, 총괄관리자 - 최고관리자에게)
     *
     * 3. empId의 role 이 최고관리자인 경우
     * 승인을 받지 않는다
     *
     * @param empId
     * @return
     */
    public void findApvlByEmp(Long empId) {
        Employee emp = findByEmployeeById(empId);

        if(emp.getRole().equals(EmpRole.EMPLOYEE)) {
            //직원일 경우

       } else {
            //관리자일 경우

        }
    }

    /**
     * 승인권자 리스트 호출
     *
     * 휴가, 출퇴근 관련 요청을 보내는 직원에 대한 상위조직의 승인권자 리스트를 반환한다.
     * 반환되는 승인권자는 중간관리자, 최고관리자 권한의 직원이며
     * 요청하는 직원의 조직레벨부터 최상위 조직의 승인권자까지 찾아서 리스트로 반환한다.
     * @param corId : 요청보내는 직원이 소속된 corporation 의 ID
     * @param depId : 요청보내는 직원이 소속된 department 의 ID
     * @return
     */
    public List<EmployeeResponseDto> findApprovalLine(Long corId, Long depId){

        return employeeRepository.findApprovalLine(corId, depId)
                .stream()
                .map(EmployeeResponseDto::convertToDto)
                .collect(Collectors.toList());
    }
}