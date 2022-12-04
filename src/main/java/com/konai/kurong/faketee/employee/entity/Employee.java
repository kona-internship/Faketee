package com.konai.kurong.faketee.employee.entity;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    public void update(Employee employee) {
        this.name = name;
        this.role = role;
        this.position = position;
        this.department = department;
    }

    public void updateVal() {
        this.val = "T";
    }
}
