package com.konai.kurong.faketee.attend.entity;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="ATD_REQ_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="ATD_REQ_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "ATD_REQ")
public class AttendRequest extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ATD_REQ_ID_GENERATOR"
    )
    private Long id;

    @Column(name = "ATD_REQ_DATE", nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String val;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "EMP_ID", nullable = false)
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "ITG_DRAFT_ID", nullable = false)
    private Draft draft;
}
