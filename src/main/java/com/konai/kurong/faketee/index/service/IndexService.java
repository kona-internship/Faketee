package com.konai.kurong.faketee.index.service;

import com.konai.kurong.faketee.account.util.Role;
import com.konai.kurong.faketee.auth.dto.SessionUser;
import com.konai.kurong.faketee.corporation.dto.CorporationResponseDto;
import com.konai.kurong.faketee.corporation.service.CorporationService;
import com.konai.kurong.faketee.employee.dto.EmployeeSessionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.konai.kurong.faketee.utils.URI.*;

@RequiredArgsConstructor
@Service
public class IndexService {

    private final CorporationService corporationService;

    public String splitUri(SessionUser sessionUser) {

        if (sessionUser != null && sessionUser.getRole().equals(Role.USER) && sessionUser.getEmployeeList().isEmpty())
            return INIT_USR_LOGIN_REDIRECT_URI;
        else if (sessionUser != null && sessionUser.getRole().equals(Role.USER) && !sessionUser.getEmployeeList().isEmpty())
            return USR_LOGIN_REDIRECT_URI;
        else
            return INDEX_WELCOME_URI;
    }

    public List<CorporationResponseDto> loadCorLists(List<EmployeeSessionResponseDto> employeeList){

        List<CorporationResponseDto> corList = new ArrayList<>();
        for(EmployeeSessionResponseDto employee : employeeList){
            corList.add(corporationService.findByEmpId(employee.getId()));
        }
        return corList;
    }
}
