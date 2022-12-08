package com.konai.kurong.faketee.employee.utils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReqEmpInfo {
    private Long id;

    private String name;

    private EmpRole role;

    private String val;
}
