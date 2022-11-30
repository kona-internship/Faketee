package com.konai.kurong.faketee.location.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.service.DepLocService;
import com.konai.kurong.faketee.location.dto.LocationResponseDto;
import com.konai.kurong.faketee.location.dto.LocationSaveRequestDto;
import com.konai.kurong.faketee.location.entity.Location;
import com.konai.kurong.faketee.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationService {
    private final CorporationRepository corporationRepository;
    private final LocationRepository locationRepository;
    private final DepLocService depLocService;

    /**
     * 출퇴근 장소 등록
     * 장소 등록하면서 직원도 넣어준다.
     *
     * @param corId 장소를 등록할 회사 아이디
     * @param requestDto 장소에 대한 내용(프로퍼티)
     */
    public void registerLocation(Long corId, LocationSaveRequestDto requestDto) {
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        Location newLoc = requestDto.toEntity(corporation);
        locationRepository.save(newLoc);
    }

    /**
     *
     * 출퇴근 장소 목록 불러오기
     *
     * @param corId 장소 목록을 가져올 회사 아이디
     * @return Location에서 LocationResponseDto로 변환된 리스트 반환
     */
    public List<LocationResponseDto> getLocList(Long corId){
        return LocationResponseDto.converToDtoList(locationRepository.findLocationByCorporationIdOrderById(corId));
    }

    /**
     * 출퇴근 장소 삭제
     * 지우려는 장소와 연관된 dep_loc에 값이 있을 경우 -1를 반환하여 지울 수 없다는 알림을 띄운다.
     * 값이 없을 경우에는 지우기 성공
     *
     * @param locId 지우려는 장소 아이디
     * @return 삭제가능하면 true, 삭제불가면 false
     */
    public boolean removeLocation(Long locId){
        if(depLocService.existDepLocBylocId(locId)) {
            //장소에 출근하는 조직이 존재한다는 말
            return false;
        }else{//없으면 삭제
            locationRepository.deleteById(locId);
            return true;
        }
    }
}
