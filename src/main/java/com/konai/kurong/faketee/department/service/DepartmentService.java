package com.konai.kurong.faketee.department.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CorporationRepository corporationRepository;
    private final LocationRepository locationRepository;
    private final DepLocRepository depLocRepository;

    public void registerDepartment(Long corId, DepartmentSaveRequestDto requestDto){

        //추가사항: 등록하려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

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
                .createdId("user_1") //임의로 넣어둠
                .build();

        // 디비에 조직 저장하기
        departmentRepository.save(department);

        // 조직과 연결하려는 출퇴근 장소 불러오기
        Location location = locationRepository.findById(requestDto.getLocationId()).orElseThrow();
//                location = Location.builder()
//                .name("test_name")
//                .address("test_address")
//                .lat(new BigDecimal("10.12345"))
//                .lng(new BigDecimal("10.12345"))
//                .radius(100L)
//                .corporation(corporation)
//                .createdDateTime(LocalDateTime.now())
//                .createdId("user_1") //임의로 넣어둠
//                .build();
//        location = locationRepository.save(location);

        // 조직과 출퇴근 장소 연결하여 디비 저장
        depLocRepository.save(DepLoc.builder()
                .location(location)
                .department(department)
                .createdDateTime(LocalDateTime.now())
                .createdId("user_1") //임의로 넣어둠
                .build());

    }

    public List<DepartmentResponseDto> getDepList(Long corId){

        //추가사항: 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        return DepartmentResponseDto.convertToDtoList(departmentRepository.findAllByCorporation_Id(corId));
    }

    public void removeDep(Long depId){

    }

}
