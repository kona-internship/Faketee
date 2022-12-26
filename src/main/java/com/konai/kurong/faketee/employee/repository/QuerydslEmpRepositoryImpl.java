package com.konai.kurong.faketee.employee.repository;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.employee.dto.EmployeeResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.konai.kurong.faketee.employee.entity.QEmployee.employee;
import static com.konai.kurong.faketee.department.entity.QDepartment.department;

@Slf4j
@RequiredArgsConstructor
public class QuerydslEmpRepositoryImpl implements QuerydslEmpRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Employee> getEmployeeByUserAndCorAndVal(Long usrId, Long corId, String val){
        if(usrId == null){
            return jpaQueryFactory
                    .selectFrom(employee)
                    .where(employee.user.id.isNull(), employee.corporation.id.eq(corId), employee.val.eq(val))
                    .fetch();
        }
        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.user.id.eq(usrId), employee.corporation.id.eq(corId), employee.val.eq(val))
                .fetch();
    }

    @Override
    public List<Employee> findByDepId(Long depId) {

        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.department.id.eq(depId), employee.val.eq("T"))
                .fetch();
    }

    @Override
    public List<Employee> findByUserId(Long userId) {

        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.user.id.eq(userId))
                .fetch();
    }

    public List<Employee> getEmployeeByDepAndPos(List<Long> deps, List<Long> pos) {
        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.department.id.in(deps).and(employee.position.id.in(pos)))
                .fetch();
    }

    @Override
    public Employee findAdminApproval(Long corId) {

        return jpaQueryFactory
                .selectFrom(employee)
                .where(employee.corporation.id.eq(corId), employee.role.eq(EmpRole.ADMIN))
                .fetchOne();
    }

    @Override
    public List<Employee> findApprovalsById(List<Long> empId) {

        List<Employee> approvals = new ArrayList<>();
        for(int i=0; i<empId.size(); i++){
            approvals.add(
                    jpaQueryFactory
                    .selectFrom(employee)
                    .where(employee.id.eq(empId.get(i)))
                    .fetchOne());
        }
        return approvals;
    }

    /**
     * TODO:  승인권자 찾기
     * 1. 관리자 리스트 뽑고
     * 2. 상위 조직 ID 들을 탐색한 후
     * 3. 관리자 리스트에서 탐색한 조직 ID와 비교하여 승인권자를 선정한다
     */
    @Override
    public List<Employee> findApprovalLine(Long corId, Long depId){

        List<Employee> managerList = findManagerList(corId);
        List<Long> upperDepartmentsId = findUpperDepartmentsId(depId);
        List<Employee> approvalLine = new ArrayList<>();

        for(Employee managers : managerList){
            for(Long departmentId : upperDepartmentsId){
                if(managers.getDepartment().getId().equals(departmentId)) {
                    approvalLine.add(managers);
                }
            }
        }
        return approvalLine;
    }

    private List<Employee> findManagerList(Long corId){

        return jpaQueryFactory
                .selectFrom(employee)
                .where(
                        employee.corporation.id.eq(corId),
                        employee.role.ne(EmpRole.EMPLOYEE),
                        employee.role.ne(EmpRole.ADMIN)
                )
                .fetch();
    }

    private List<Long> findUpperDepartmentsId(Long depId){

        List<Long> supperDepartmentsId = new ArrayList<>();
        Department currentDepartment;
        Department superDepartment;

        while (depId != 0L){
            currentDepartment = jpaQueryFactory
                    .selectFrom(department)
                    .where(department.id.eq(depId))
                    .fetchOne();

            assert currentDepartment != null;
            superDepartment = currentDepartment.getSuperDepartment();
            supperDepartmentsId.add(currentDepartment.getId());

            if(superDepartment!=null)
                depId = superDepartment.getId();
            else
                depId = 0L;
        }
        return supperDepartmentsId;
    }

}