package com.konai.kurong.faketee.vacation.entity;

import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "VAC_TYPE_GENERATOR",
        sequenceName = "VAC_TYPE_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "VAC_TYPE")
@Entity
public class VacType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "VAC_TYPE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SUB")
    private Double sub;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "VAC_GROUP_ID")
    private VacGroup vacGroup;
}
