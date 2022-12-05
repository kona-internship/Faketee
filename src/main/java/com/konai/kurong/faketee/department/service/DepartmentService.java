package com.konai.kurong.faketee.department.service;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.corporation.repository.CorporationRepository;
import com.konai.kurong.faketee.department.dto.DepartmentModifyRequestDto;
import com.konai.kurong.faketee.department.dto.DepartmentRemoveRequestDto;
import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.department.dto.DepartmentSaveRequestDto;
import com.konai.kurong.faketee.department.entity.DepLoc;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.department.repository.DepLocRepository;
import com.konai.kurong.faketee.department.repository.DepartmentRepository;
import com.konai.kurong.faketee.location.dto.LocationResponseDto;
import com.konai.kurong.faketee.location.entity.Location;
import com.konai.kurong.faketee.location.repository.LocationRepository;
import com.konai.kurong.faketee.location.repository.QuerydslLocRepository;
import com.konai.kurong.faketee.utils.exception.custom.department.DepartmentNotFoundException;
import com.konai.kurong.faketee.utils.exception.custom.department.LowDepAlreadyExistException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CorporationRepository corporationRepository;
    private final LocationRepository locationRepository;
    private final DepLocRepository depLocRepository;

    /**
     * 조직 등록.
     * 회사와 상위 조직을 불러온 후 조직을 만들어 디비에 조직을 저장한다.
     * 조직과 연결하려는 출퇴근 장소 목록을 매핑 테이블(DEP_LOC)에 저장한다.
     * 입력받은 출퇴근 장소가 없을 경우 회사에 속한 모든 출퇴근 장소를 가진다.
     *
     * @param corId
     * @param requestDto
     */
    @Transactional
    public void registerDepartment(Long corId, DepartmentSaveRequestDto requestDto) {

        //추가사항: 등록하려는 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        Department superDep = null;
        Long level = 0L;

        // 회사 불러오기
        Corporation corporation = corporationRepository.findById(corId).orElseThrow();

        // 상위 조직 불러오기
        // 상위 조직이 있을 경우
        if (requestDto.getSuperId() != null) {
            Optional<Department> department = departmentRepository.findById(requestDto.getSuperId());
            // 상위 조직 객체 불러오기
            if(department.isEmpty()){
                throw new RuntimeException();
            }
            superDep = department.get();
            level = superDep.getLevel() + 1;
        }

        // 조직 만들기
        Department department = Department.builder()
                .name(requestDto.getName())
                .level(level)
                .corporation(corporation)
                .superDepartment(superDep)
                .build();

        // 디비에 조직 저장하기
        departmentRepository.save(department);

        List<Location> locationList = new ArrayList<>();
        // 조직과 연결하려는 출퇴근 장소 불러오기
        if (requestDto.getSuperId() == null && requestDto.getLocationIdList().size() == 0) {
            //상위 조직이 없고 출퇴근장소도 고르지 않았을 경우
            locationList = locationRepository.findLocationByCorporationIdOrderById(corId);
        }else if(requestDto.getSuperId() != null && requestDto.getLocationIdList().size() == 0){
            //상위 조직 있고 출퇴근 장소를 고르지 않았을 경우
            List<DepLoc> depLocList = depLocRepository.findAllByDepartment_Id(superDep.getId());

            for(int i = 0; i < depLocList.size(); i++){
                Location location = depLocList.get(i).getLocation();
                locationList.add(location);
            }
        }else {
            // 출퇴근 장소 있는 경우
            locationList = locationRepository.findLocationByIdIn(requestDto.getLocationIdList());
        }

        // 조직과 출퇴근 장소 연결하여 디비 저장
        List<DepLoc> depLocList = new ArrayList<>();
        for (Location location : locationList) {
            depLocList.add(DepLoc.builder()
                    .location(location)
                    .department(department)
                    .build());
        }
        depLocRepository.saveAll(depLocList);
    }

    /**
     * 조직 목록 불러오기.
     * 조직 목록을 디비에서 가져온 뒤, 반환 형식에 맞게 변환해준다.
     *
     * @param corId
     * @return
     */
    public List<DepartmentResponseDto> getDepList(Long corId) {

        //추가사항: 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        return DepartmentResponseDto.convertToDtoList(departmentRepository.findAllByCorporation_Id(corId));
    }

    /**
     * 현재 조직과 그에 연결된 하위 조직들을 반환한다.
     *
     * @param dep 조직
     * @return 조직과 조직의 하위조직들의 리스트
     */

    public List<Department> getSubDepList(Department dep) {
        List<Department> depList = new ArrayList<>();
        Stack<Department> stack = new Stack<>();

        stack.push(dep);
        while (!stack.empty()) {
            Department d = stack.pop();
            depList.add(d);
            for (int i = 0; i < d.getChildDepartments().size(); i++) {
                stack.push(d.getChildDepartments().get(i));
            }
        }

        return depList;
    }

    /**
     * 부서(조직)에 대한 상세 사항들을 모두 가져온다
     * 부서(조직), 출퇴근 장소, 하위 조직들
     *
     * @param depId
     * @return
     */

    @Transactional
    public Result<?> getAllLowDepWithLoc(Long depId) {

        //해당 부서
        Optional<Department> department = departmentRepository.findById(depId);
        Department dep = department.get();

        //해당 부서 출퇴근 장소
        List<DepLoc> deplocs = depLocRepository.findAllByDepartment_Id(depId);
        List<Location> loc = new ArrayList<>();
        for (DepLoc deploc : deplocs) {
            loc.add(deploc.getLocation());
        }

        //해당 부서 하위 조직들
        List<Department> subs = getSubDepList(dep);

        return new Result<>(DepartmentResponseDto.convertToDto(dep), LocationResponseDto.converToDtoList(loc), DepartmentResponseDto.convertToDtoList(subs));
    }

    public DepartmentResponseDto getDep(Long depId){
        return DepartmentResponseDto.convertToDto(departmentRepository.findById(depId).orElseThrow());
    }

    /**
     * 조직 삭제
     * requestDto에 삭제할 조직들의 id와 level이 있다.
     * level을 돌면서 가장 하위의 조직들부터 삭제해 나간다.
     *
     * @param corId
     * @param requestDto
     * @throws RuntimeException
     */

    @Transactional
    public void removeDep(Long corId, DepartmentRemoveRequestDto requestDto) throws LowDepAlreadyExistException {
        //추가사항: 리펙토링 필요, 사용자가 해당 회사의 권한을 가지고 있는지 여부 로직

        List<Long> idList = new ArrayList<>();
        List<Long> levelList = new ArrayList<>();


        //어떤 객체이서 리스트 롱타임으로 2개
        for (Map<Long, Long> map : requestDto.getRemoveDepList()) {
            map.forEach((id, level) -> {
                idList.add(id);
                levelList.add(level);
            });
        }

        Long min = Long.MAX_VALUE;
        Long max = -1L;
        for (Long i : levelList) {
            if (max < i) {
                max = i;
            }
            if (min > i) {
                min = i;
            }
        }
        while (max >= min) {
            for (int i = 0; i < levelList.size(); i++) {
                if (levelList.get(i) == max) {
                        depLocRepository.deleteDepLocByDepartmentId(idList.get(i));
                        departmentRepository.deleteById(idList.get(i));

                }
            }
            max--;
        }

    }

    /**
     * 조직 수정.
     * 하위 조직의 출퇴근 장소도 같이 수정할 것인지 여부를 확인하고,
     * 조직 또는 조직들과 연결된 출퇴근 장소 매핑을 디비 테이블(DEP_LOC)에서 삭제시키고
     * 요청된 출퇴근 장소들과 조직 또는 조직들을 연결시켜준다.
     *
     * @param corId
     * @param depId
     * @param requestDto
     */
    @Transactional
    public void modifyDep(Long corId, Long depId, DepartmentModifyRequestDto requestDto){

        // 하위 조직도 같이 수정할 것인지 여부 확인
        if(requestDto.getIsModifyLow()){
            // 자신과 하위 조직들에 연결된 출퇴근 장소를 그것들이 매핑된 테이블(DEP_LOC)에서 전부 삭제해준다.
            depLocRepository.deleteDepLocsByDepIds(requestDto.getLowDepartmentIdList());

            // 수정하고자 하는 출퇴근 장소를 디비에서 불러온 뒤, 자신과 하위 조직들과 연결하여 매핑 테이블(DEP_LOC)에 넣어준다.
            List<DepLoc> depLocList = new ArrayList<>();
            List<Location> locations = locationRepository.findLocationsByIds(requestDto.getLocationIdList());
            for(Long lowDepId : requestDto.getLowDepartmentIdList()){
                Department lowDep = departmentRepository.findById(lowDepId).orElseThrow();
                // 불러온 조직이 자신(root)일 경우 조직의 이름 변경
                if(lowDep.getId() == depId) {
                    lowDep.changeName(requestDto.getName());
                }

                for(Location location : locations) {
                    DepLoc lowDepLoc = DepLoc.builder()
                            .location(location)
                            .department(lowDep)
                            .build();
                    depLocList.add(lowDepLoc);
                }
            }
            depLocRepository.saveAll(depLocList);

        }else{
            // 조직과 연결된 출퇴근 장소를 그것들이 매핑된 테이블(DEP_LOC)에서 전부 삭제해준다.
            depLocRepository.deleteAllByDepartment_Id(depId);

            // 조직을 불러와 이름을 변경해준다.
            Department department = departmentRepository.findById(depId).orElseThrow();
            department.changeName(requestDto.getName());

            // 요청된 출퇴근 장소들을 불러온다.
            List<Location> locationList = locationRepository.findLocationsByIds(requestDto.getLocationIdList());

            // 출퇴근 장소와 조직을 연결하여 디비에 매핑된 테이블(DEP_LOC)에 저장한다.
            List<DepLoc> depLocList = new ArrayList<>();
            for(Location location : locationList){
                depLocList.add(DepLoc.builder()
                        .location(location)
                        .department(department)
                        .build());
            }
            depLocRepository.saveAll(depLocList);
        }
    }

    @Getter
    @Setter
    static class Result<T> {
        private T dep;
        private T loc;

        private T sub;

        public Result(T dep, T loc, T sub) {
            this.dep = dep;
            this.loc = loc;
            this.sub = sub;
        }
    }

    public Department findById(Long id){

        return departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException());
    }
}
