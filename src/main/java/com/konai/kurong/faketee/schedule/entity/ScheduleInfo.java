package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime date;

    private String state;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
//    @ManyToOne
//    @JoinColumn(name = "EMP_ID")
//    private Employee employee;

}
