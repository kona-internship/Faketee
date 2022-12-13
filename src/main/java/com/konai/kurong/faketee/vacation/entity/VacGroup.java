package com.konai.kurong.faketee.vacation.entity;


import com.konai.kurong.faketee.corporation.entity.Corporation;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(
        name = "VAC_GROUP_SEQUENCE_GENERATOR",
        sequenceName = "VAC_GROUP",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "VAC_GROUP")
@Entity
public class VacGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "VAC_GROUP_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "COR_ID")
    private Corporation corporation;
}
