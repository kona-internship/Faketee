package com.konai.kurong.faketee.department.service;

import com.konai.kurong.faketee.department.entity.DepLoc;
import com.konai.kurong.faketee.department.repository.DepLocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepLocService {

    private final DepLocRepository depLocRepository;

    /**
     * 출퇴근 장소(locId)에 해당되는 데이터가 존재하면 삭제 불가함을 판별
     *
     * @param locId
     * @return true면 삭제 불가, false면 삭제 가능
     */
    public boolean existDepLocBylocId(Long locId){
        Optional<DepLoc> depLoc = depLocRepository.findDepLocByLocationId(locId);
        if(depLoc.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
