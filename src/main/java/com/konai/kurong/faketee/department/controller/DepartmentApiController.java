package com.konai.kurong.faketee.department.controller;

import com.konai.kurong.faketee.department.dto.DepartmentModifyRequestDto;
import com.konai.kurong.faketee.department.dto.DepartmentRemoveRequestDto;
import com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto;
import com.konai.kurong.faketee.department.service.DepartmentService;
import com.konai.kurong.faketee.employee.utils.EmpAuth;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.employee.utils.ReqEmp;
import com.konai.kurong.faketee.employee.utils.ReqEmpInfo;
import com.konai.kurong.faketee.utils.exception.custom.department.LowDepAlreadyExistException;
import com.konai.kurong.faketee.utils.exception.response.CustomException;
import com.konai.kurong.faketee.utils.exception.response.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

import static com.konai.kurong.faketee.employee.utils.EmpAuthInterceptor.AUTH_EMP_KEY;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corporation/{corId}/dep")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    /**
     * 조직 등록
     *
     * @param corId
     * @param requestDto @NotNull String name
     *                   Long superId
     *                   @NotNull List<Long> locationIdList
     * @return
     */
    @EmpAuth(role = EmpRole.GENERAL_MANAGER, onlyLowDep = false)
    @PostMapping()
    public ResponseEntity<?> registerDep(@PathVariable(name = "corId") Long corId,
                                         @Valid @RequestBody DepartmentSaveRequestDto requestDto,
                                         @ReqEmp ReqEmpInfo reqEmpInfo){
        log.info("==================controller=====================");
        log.info("reqEmpInfo: "+ reqEmpInfo.getId());

        departmentService.registerDepartment(corId, requestDto);
        log.info("==================end=========================");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 조직 목록 불러오기
     *
     * @param corId
     * @return
     */
    @EmpAuth(role = EmpRole.GROUP_MANAGER, onlyLowDep = false)
    @GetMapping("/list")
    public ResponseEntity<?> getDepList(@PathVariable(name = "corId") Long corId){

        return new ResponseEntity<>(departmentService.getDepList(corId), HttpStatus.OK);
    }

    /**
     * 조직 삭제
     *
     * @param corId
     * @param requestDto List<Map<Long, Long>> removeDepList
     * @return
     * @throws RuntimeException
     */
    @EmpAuth(role = EmpRole.GENERAL_MANAGER)
    @PostMapping("/remove/{depId}")
    public ResponseEntity<?> removeDep(@PathVariable(name = "corId") Long corId,
                                       @RequestBody DepartmentRemoveRequestDto requestDto) throws RuntimeException{

        //여기서 객체에 리스트 두개 롱타입으로 조직id와 조직 level의 값을 전달받는다.
        departmentService.removeDep(corId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @EmpAuth(role = EmpRole.EMPLOYEE, onlyLowDep = false)
    @PostMapping("/detail/{depId}")
    public ResponseEntity<?> detailDep(@PathVariable(name = "depId") Long depId){

        return new ResponseEntity<>(departmentService.getAllLowDepWithLoc(depId), HttpStatus.OK);
    }

    /**
     * 조직 수정
     *
     * @param corId
     * @param depId
     * @param requestDto String name
     *                   List<Long> lowDepartmentIdList
     *                   List<Long> locationIdList
     *                   @NotNull Boolean isModifyLow
     * @return
     */
    @EmpAuth(role = EmpRole.GROUP_MANAGER)
    @PostMapping("/{depId}/mod")
    public ResponseEntity<?> modifyDep(@PathVariable(name = "corId") Long corId,
                                       @PathVariable(name = "depId") Long depId,
                                       @RequestBody DepartmentModifyRequestDto requestDto){
        departmentService.modifyDep(corId, depId, requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ExceptionResponse DataIntegrityViolation(DataIntegrityViolationException e) {

        Boolean isDepDepLoc = Arrays.stream(e.getMessage().split(";")).anyMatch(s->s.equals(" constraint [C##FAKETEE.FK_DEP_TO_DEP_LOC]"));
        ExceptionResponse errorResponse = ExceptionResponse.builder()
                .message("exception")
                .build();
        if(isDepDepLoc) {
            LowDepAlreadyExistException exception = new LowDepAlreadyExistException();
            errorResponse.addError(CustomException.builder()
                    .message(exception.getMessage())
                    .build());
        }

        return errorResponse;
    }
}
