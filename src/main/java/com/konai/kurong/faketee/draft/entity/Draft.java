package com.konai.kurong.faketee.draft.entity;

import com.konai.kurong.faketee.employee.entity.Employee;
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
        name = "DRAFT_SEQUENCE_GENERATOR",
        sequenceName = "DRAFT_SEQUENCE",
        initialValue = 1,
        allocationSize = 1
)
@Table(name = "DRAFT")
@Entity
public class Draft extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "DRAFT_SEQUENCE_GENERATOR")
    @Column(name = "ID")
    private Long id;

    @Column(name = "APVL_DATE")
    private LocalDateTime approvalDate;

    @Column(name = "REQ_DATE")
    private LocalDateTime requestDate;

    @Column(name = "APVL_MSG")
    private String approvalMessage;

    @Column(name = "STATE_CODE")
    private String stateCode;

    @Column(name = "REQ_MSG")
    private String requestMessage;

    @Column(name = "REQ_TYPE")
    private String requestType;

    @Column(name = "CRUD_TYPE")
    private String crudType;

    @ManyToOne
    @JoinColumn(name = "REQ_EMP_ID")
    private Employee requestEmployee;

    @ManyToOne
    @JoinColumn(name = "APVL_EMP_ID_1")
    private Employee approvalEmp1;

    @ManyToOne
    @JoinColumn(name = "APVL_EMP_ID_2")
    private Employee approvalEmp2;

}
