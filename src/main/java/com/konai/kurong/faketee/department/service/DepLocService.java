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

    public boolean existDepLocBylocId(Long locId){
        Optional<DepLoc> depLoc = depLocRepository.findDepLocByLocationId(locId);
        if(depLoc.isPresent()){
            return true;
        }else{
            return false;
        }
    }
}
