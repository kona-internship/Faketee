package com.konai.kurong.faketee.employee.entity;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import com.konai.kurong.faketee.vacation.entity.VacInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="EMP_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="EMP_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "EMP")
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "EMP_ID_GENERATOR"
    )
    private Long id;

    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmpRole role;

    /**
     * W(wait) : 직원 등록을 했으며, 합류 초대 메일 인증을 기다리고 있음
     * T(true) : 합류 초대 메일 인증을 완료함
     * F(false) : 비활성화
     */
    @Column(nullable = false)
    private String val;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COR_ID", nullable = false)
    private Corporation corporation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USR_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "POS_ID")
    private Position position;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DEP_ID")
    private Department department;

    @OneToOne
    @JoinColumn(name = "EMP_INFO_ID")
    private EmployeeInfo employeeInfo;

    @OneToMany(mappedBy = "employee", fetch = LAZY, cascade = CascadeType.REMOVE)
    private List<VacInfo> vacationInfo;

    public void update(Employee requestDto) {
        this.name = requestDto.getName();
        this.role = requestDto.getRole();
        this.position = requestDto.getPosition();
        this.department = requestDto.getDepartment();
    }

    public void deactivate() {
        this.val = "F";
    }

    public void join(User user){
        this.val = "T";
        this.user = user;
    }
}
