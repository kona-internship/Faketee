package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.entity.Position;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "TMP_SEQUENCE_GENERATOR",
        sequenceName = "TMP_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "TMP")
@Entity
public class Template extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "TMP_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "END_TIME")
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEP_ID")
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POS_ID")
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCH_TYPE_ID")
    private ScheduleType scheduleType;
}
