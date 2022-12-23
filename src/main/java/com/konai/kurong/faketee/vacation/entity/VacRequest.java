package com.konai.kurong.faketee.vacation.entity;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.utils.jpa_auditing.BaseEntity;
import com.konai.kurong.faketee.vacation.util.RequestVal;
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
        name = "VAC_DATE_REQ_GENERATOR",
        sequenceName = "VAC_DATE_REQ_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "VAC_REQ")
@Entity
public class VacRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "VAC_DATE_REQ_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "VAC_REQ_DATE")
    private LocalDateTime date;

    @Column(name = "VAL")
    private RequestVal val;

    @ManyToOne
    @JoinColumn(name = "VAC_TYPE_ID")
    private VacType vacType;

    @ManyToOne
    @JoinColumn(name = "DRAFT_ID")
    private Draft draft;

    @ManyToOne
    @JoinColumn(name = "EMP_ID")
    private Employee employee;

}
