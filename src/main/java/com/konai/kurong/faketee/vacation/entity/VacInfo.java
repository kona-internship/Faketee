package com.konai.kurong.faketee.vacation.entity;

import com.konai.kurong.faketee.employee.entity.Employee;
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
        name = "VAC_INFO_SEQUENCE_GENERATOR",
        sequenceName = "VAC_INFO_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "VAC_INFO")
@Entity
public class VacInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "VAC_INFO_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "REMAINING")
    private Double remaining;

    @Column(name = "USED")
    private Double used;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "VAC_GROUP_ID")
    private VacGroup vacGroup;
}
