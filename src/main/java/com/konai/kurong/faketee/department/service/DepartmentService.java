package com.konai.kurong.faketee.department.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.dto.DepartmentRemoveRequestDto;
import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto;
import com.konai.kurong.faketee.department.entity.DepLoc;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.repository.DepLocRepository;
import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.location.entity.Location;
import com.konai.kurong.faketee.location.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CorporationRepository corporationRepository;
    private final LocationRepository locationRepository;
    private final DepLocRepository depLocRepository;

    public void registerDepartment(Long corId, DepartmentSaveRequestDto requestDto){

        //추가사항: 등록하려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직, 여러 개 로케이션 추가 필요

        List<DepLoc> superDepLocList;
        Department superDep = null;
        Long level = 0L;

        // 회사 불러오기
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        // 상위 조직 불러오기
        // 상위 조직이 있을 경우
        if(requestDto.getSuperId() != null) {
            // 상위 조직과 연관된 출퇴근장소 리스트 불러오기
            superDepLocList = depLocRepository.findAllByDepartment_Id(requestDto.getSuperId());
            // 해당 아이디로 조직을 불러오지 못할 경우
            if (superDepLocList.isEmpty()) {
                throw new RuntimeException();
            }
            // 상위 조직 객체 불러오기
            superDep = superDepLocList.get(0).getDepartment();
            level = superDep.getLevel() + 1;
        }

        // 조직 만들기
        Department department = Department.builder()
                .name(requestDto.getName())
                .level(level)
                .corporation(corporation)
                .superDepartment(superDep)
                .createdDateTime(LocalDateTime.now())
                .createdId(100L) //임의로 넣어둠
                .build();

        // 디비에 조직 저장하기
        departmentRepository.save(department);

        // 조직과 연결하려는 출퇴근 장소 불러오기
        List<Location> locationList = locationRepository.findLocationsByIds(requestDto.getLocationIdList());

        // 조직과 출퇴근 장소 연결하여 디비 저장
        List<DepLoc> depLocList = new ArrayList<>();
        for(Location location : locationList){
            depLocList.add(DepLoc.builder()
                    .location(location)
                    .department(department)
                    .createdDateTime(LocalDateTime.now())
                    .createdId(100L) //임의로 넣어둠
                    .build());
        }
        depLocRepository.saveAll(depLocList);

    }

    public List<DepartmentResponseDto> getDepList(Long corId){

        //추가사항: 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        return DepartmentResponseDto.convertToDtoList(departmentRepository.findAllByCorporation_Id(corId));
    }

    public void removeDep(Long corId, DepartmentRemoveRequestDto requestDto){
        //추가사항: 리펙토링 필요, 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        List<Long> idList = new ArrayList<>();
        List<Long> levelList = new ArrayList<>();

        //어떤 객체이서 리스트 롱타임으로 2개빼내
        for(Map<Long, Long> map : requestDto.getRemoveDepList()){
            map.forEach((id, level)->{
                idList.add(id);
                levelList.add(level);
            });
        }

        Long min = Long.MIN_VALUE;
        Long max = -1L;
        for(Long i : levelList){
            if(max < i){
                max = i;
            }
            if(min > i){
                min = i;
            }
        }
        while(max < min){
            for(int i = 0; i < levelList.size(); i++){
                if(levelList.get(i) == max){
                    depLocRepository.deleteDepLocByDepartmentId(idList.get(i));
                    departmentRepository.deleteById(idList.get(i));
                }
            }
            max--;
        }


    }
}
