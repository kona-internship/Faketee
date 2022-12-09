package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCH_TYPE")
public class ScheduleType extends BaseEntity {
    @Id
    @SequenceGenerator(name = "SCH_TYPE_ID_GENERATOR", sequenceName = "SCH_TYPE_SEQUENCE", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCH_TYPE_ID_GENERATOR")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;
}
