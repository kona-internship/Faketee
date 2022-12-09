package com.konai.kurong.faketee.corporation.controller;

import com.konai.kurong.faketee.auth.LoginUser;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.corporation.dto.CorporationSaveRequestDto;
import com.konai.kurong.faketee.corporation.service.CorporationService;
import com.konai.kurong.faketee.employee.dto.EmployeeJoinRequestDto;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CorporationApiController {
    private final CorporationService corporationService;
    private final EmployeeService employeeService;

    /**
     * 회사 등록
     *
     * @param requestDto
     * @return
     */
    @PostMapping("/api/corporation")
    public int registerCorporation(@Valid @RequestBody CorporationSaveRequestDto requestDto,
                                   @LoginUser SessionUser user) {

        Long id = corporationService.registerCorporation(requestDto, user.getId(), user.getName(), user.getEmail());

        return Math.toIntExact(id);
    }

    /**
     * 회사 합류
     */
    @PostMapping("/api/join-corporation")
    public ResponseEntity<?> joinCorporation(@LoginUser SessionUser user,
                                             @Valid @RequestBody EmployeeJoinRequestDto requestDto) {

        employeeService.joinEmployee(user.getId(), requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
