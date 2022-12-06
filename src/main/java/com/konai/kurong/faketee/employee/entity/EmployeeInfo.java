package com.konai.kurong.faketee.employee.entity;

import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="EMP_INFO_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="EMP_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "EMP_INFO")
public class EmployeeInfo extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "EMP_INFO_ID_GENERATOR"
    )
    private Long id;

    @Column(name="JOIN_DATE")
    private LocalDateTime joinDate;

    @Column(name="FREE_DATE")
    private LocalDateTime freeDate;

    private String major;

    private String cert;

    private String info;

    @Column(name="EMP_NO")
    private Long empNo;

    @Column(nullable = false)
    private String email;

    @Column(name="JOIN_CODE", nullable = false)
    private String joinCode;

    public void update(EmployeeInfo requestDto) {
        this.joinDate = requestDto.getJoinDate();
        this.freeDate = requestDto.getFreeDate();
        this.major = requestDto.getMajor();
        this.cert = requestDto.getCert();
        this.info = requestDto.getInfo();
        this.empNo = requestDto.getEmpNo();
    }
}
