package com.konai.kurong.faketee.employee.entity;

import com.konai.kurong.faketee.account.entity.User;
import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.employee.utils.EmpRole;
import com.konai.kurong.faketee.position.entity.Position;
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
public class Employee {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "EMP_ID_GENERATOR"
    )
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmpRole role;

    private String joinCode;

    @Column(nullable = false)
    private Boolean value;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COR_ID", nullable = false)
    private Corporation corporation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USR_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DEP_ID")
    private Position position;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "DEP_ID")
    private Department department;
}
