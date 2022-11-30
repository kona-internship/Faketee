package com.konai.kurong.faketee.department.repository;

import java.util.List;

public interface QuerydslDepLocRepository {
    Long deleteDepLocsByDepIds(List<Long> depIdList);
}
