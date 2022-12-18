package com.konai.kurong.faketee.department.service;

import com.konai.kurong.faketee.department.entity.DepLoc;
import com.konai.kurong.faketee.department.repository.DepLocRepository;
import com.konai.kurong.faketee.location.entity.Location;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        Optional<List<DepLoc>> depLoc = depLocRepository.findDepLocByLocationId(locId);
        if(depLoc.isPresent()){
            if(depLoc.get().size() == 0) {
                return false;
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 조직에 속한 출퇴근장소 리스트 반환
     * @param corId
     * @param depId
     * @return
     */
   public List<Location> getLocationByCorAndDep(Long corId, Long depId) {
        List<DepLoc> depLocList = depLocRepository.getDepLocsByDepIdAndCorId(depId);
        List<Location> locationList = new ArrayList<>();
        for(DepLoc dep : depLocList) {
            locationList.add(dep.getLocation());
        }

        return locationList;
   }
}
