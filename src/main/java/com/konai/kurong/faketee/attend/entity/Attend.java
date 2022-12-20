package com.konai.kurong.faketee.attend.entity;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@SequenceGenerator(
        name="ATD_ID_GENERATOR", //시퀀스 제너레이터 이름
        sequenceName="ATD_SEQUENCE", //시퀀스 이름
        initialValue=1, //시작값
        allocationSize=1 //메모리를 통해 할당할 범위 사이즈
)
@Table(name = "ATD")
public class Attend extends BaseEntity {
    @Id
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "ATD_ID_GENERATOR"
    )
    private Long id;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @Column(nullable = false)
    private String val;

    @Column(name = "ATD_DATE", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SCH_INFO_ID", nullable = false)
    private ScheduleInfo scheduleInfo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "EMP_ID", nullable = false)
    private Employee employee;
}
