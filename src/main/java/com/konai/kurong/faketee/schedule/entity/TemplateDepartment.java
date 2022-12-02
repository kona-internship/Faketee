package com.konai.kurong.faketee.schedule.entity;

import com.konai.kurong.faketee.department.entity.Department;
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
        name = "TMP_DEP_SEQUENCE_GENERATOR",
        sequenceName = "TMP_DEP_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "TMP_DEP")
@Entity
public class TemplateDepartment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "TMP_DEP_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TMP_ID")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "DEP_ID")
    private Department department;

}
