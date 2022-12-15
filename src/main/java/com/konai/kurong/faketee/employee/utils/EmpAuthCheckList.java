package com.konai.kurong.faketee.employee.utils;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EmpAuthCheckList {
    private List<Long> idList;
}
