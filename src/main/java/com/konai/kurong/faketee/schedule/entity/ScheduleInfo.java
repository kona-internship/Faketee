package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCH_INFO")
public class ScheduleInfo extends BaseEntity {
    @Id
    @SequenceGenerator(name = "SCH_INFO_ID_GENERATOR", sequenceName = "SCH_INFO_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCH_INFO_ID_GENERATOR")
    private Long id;

    @Column(name = "SCH_DATE")
    private LocalDate date;

    @Column(name = "SCH_STATE")
    private String state;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Employee employee;

    public void changeTimes(LocalTime startTime, LocalTime endTime){
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
